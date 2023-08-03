package com.ratatui.notes.user;

import com.ratatui.notes.exceptions.UserAlreadyExistsException;
import com.ratatui.notes.family.Family;
import com.ratatui.notes.note.Note;
import com.ratatui.notes.note.NoteDto;
import com.ratatui.notes.registration.RegistrationRequest;
import com.ratatui.notes.registration.token.VerificationToken;
import com.ratatui.notes.registration.token.VerificationTokenRepository;
import com.ratatui.notes.utils.Helper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final VerificationTokenRepository tokenRepository;

    @Override
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    @Override
    public User registerUser(RegistrationRequest request) {
        Optional<User> user = this.findByEmail(request.email());
        if (user.isPresent()){
            throw new UserAlreadyExistsException(
                    "User with email "+request.email() + " already exists");
        }
        var newUser = new User();
        newUser.setNickname(request.nickname());
        newUser.setEmail(request.email());
        newUser.setPassword(passwordEncoder.encode(request.password()));
        newUser.setRole(request.role());
        return userRepository.save(newUser);
    }
    @Override
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public void saveUserVerificationToken(User theUser, String token) {
        var verificationToken = new VerificationToken(token, theUser);
        tokenRepository.save(verificationToken);
    }

    @Override
    public String validateToken(String theToken) {
        VerificationToken token = tokenRepository.findByToken(theToken);
        if(token == null){
            return "Invalid verification token";
        }
        User user = token.getUser();
        Calendar calendar = Calendar.getInstance();
        if ((token.getExpirationTime().getTime() - calendar.getTime().getTime()) <= 0){
            tokenRepository.delete(token);
            return "Token already expired";
        }
        user.setEnabled(true);
        userRepository.save(user);
        return "valid";
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getFamilyUsers(Family family){
        return userRepository.findAllByFamily(family);
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
