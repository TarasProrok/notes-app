package com.ratatui.notes.user;

import com.ratatui.notes.note.Note;
import com.ratatui.notes.note.NoteDto;
import com.ratatui.notes.utils.Helper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public void deleteUserById(UUID id) {
        userRepository.deleteById(id);
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
        userRepository.save(userMapper.mapDtoToEntity(dto));
    }

    public void deleteUserFamily(UUID userId) {
        User userById = findUserById(userId);
        userById.setFamily(null);
        userRepository.save(userById);
    }

    public void createNewUser(UserDTO userDTO) {
        User user = new User();
        user.setEmail(user.getEmail());
        user.setBirthDate(userDTO.getBirthDate());
        user.setNickname(userDTO.getNickname());
        user.setUserId(userDTO.getId());
        user.setEnable(userDTO.isEnable());
        user.setGenderId(userDTO.getGenderId());
        user.setPassword(userDTO.getPassword());
        user.setCreatedDate(userDTO.getCreatedDate());
        userRepository.save(user);
    }

    public User findUserByName(String userName) {
        User user = userRepository.findByEmail(userName).orElseThrow(() ->
                new NoSuchElementException("User with email: [" + userName + "] does not exist!"));
        List<Note> notes = user.getNotes();
        user.setNotes(notes);
        return user;
    }

    public User getCurrentUser(){
        UserDetails principal = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = principal.getUsername();
        return findUserByName(username);
    }
}
