package com.ratatui.notes.user;

import com.ratatui.notes.family.Family;
import com.ratatui.notes.note.Note;
import com.ratatui.notes.utils.Helper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserValidator userValidator;

    public List<User> getFamilyUsers(Family family) {
        return userRepository.findAllByFamily(family);
    }

    public User findUserById(UUID id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new NoSuchElementException("User with id: [" + id + "] does not exist!"));
        List<Note> notes = user.getNotes();
        user.setNotes(notes);
        return user;
    }

    public void updateUser(UserDTO userDTO) {
        UserDTO dto = userMapper.mapEntityToDto(findUserById(userDTO.getId()));
        BeanUtils.copyProperties(userDTO, dto, Helper.getNullPropertyNames(userDTO));
        userValidator.setUserService(this);
        userValidator.validate(dto, false);
        userRepository.save(userMapper.mapDtoToEntity(dto));
    }

    public void updateUser(String email,
                           String password,
                           String nickname,
                           String birthDate,
                           int gender, String fullWidth) {
        User currentUser = getCurrentUser();
        UserDTO userDTO = userMapper.mapEntityToDto(currentUser);
        userDTO.setEmail(email);
        if (!birthDate.isBlank()) {
            userDTO.setBirthDate(Date.valueOf(Helper.getLocalDateFromString(birthDate)));
        }
        userDTO.setNickname(nickname);
        userDTO.setGenderId(gender);

        userDTO.setFullWidth(fullWidth != null);

        if (!password.isBlank()) {
            if (!passwordEncoder.matches(password, currentUser.getPassword())) {
                userDTO.setPassword(passwordEncoder.encode(password));
            }
        }
        updateUser(userDTO);
    }

    public void deleteUserFamily(UUID userId) {
        User userById = findUserById(userId);
        userById.setFamily(null);
        userRepository.save(userById);
    }

    public void createNewUser(String username, String password, String nickname) {
        UserDTO userDTO = UserDTO.builder()
                .email(username)
                .password(password)
                .nickname(nickname)
                .isEnable(true)
                .genderId(0)
                .role(UserRoles.ROLE_USER)
                .build();
        userValidator.setUserService(this);
        userValidator.validate(userDTO, true);
        userDTO.setPassword(passwordEncoder.encode(password));
        User user = userMapper.mapDtoToEntity(userDTO);
        userRepository.save(user);
    }

    public User findUserByName(String userName) {
        User user = userRepository.findByEmail(userName).orElseThrow(() ->
                new NoSuchElementException("User with email: [" + userName + "] does not exist!"));
        List<Note> notes = user.getNotes();
        user.setNotes(notes);
        return user;
    }

    public User getCurrentUser() {
        String username = "";
        try {
            UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            username = principal.getUsername();
        } catch (Exception e) {
            //NOP
        }
        return findUserByName(username);
    }
}
