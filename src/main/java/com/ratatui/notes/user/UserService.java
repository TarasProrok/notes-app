package com.ratatui.notes.user;

import com.ratatui.notes.note.Note;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

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
        User user = findUserById(userDTO.getId());
        user.setEmail(userDTO.getEmail());
        user.setUserType(userDTO.getUserType());
        userRepository.save(user);
    }

    public void createNewUser(UserDTO userDTO) {
        User user = new User();
        user.setEmail(user.getEmail());
        user.setBirthDate(userDTO.getBirthDate());
        user.setNickname(user.getNickname());
        user.setUserId(userDTO.getId());
        user.setEnable(true);
        user.setGenderId(user.getGenderId());
        user.setPassword(user.getPassword());
        user.setCreatedDate(Date.valueOf(LocalDate.now()));
        userRepository.save(user);
    }
}
