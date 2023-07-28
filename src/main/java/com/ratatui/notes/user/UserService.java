package com.ratatui.notes.user;

import com.ratatui.notes.note.Note;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        userRepository.deleteById(String.valueOf(id));
    }

    public User findUserById(UUID id) {
        User user = userRepository.findById(String.valueOf(id)).orElseThrow(() ->
                new NoSuchElementException("User with id: [" + id + "] does not exist!"));
        List<Note> notes = user.getNotes();
        user.setNotes(notes);
        return user;
    }

    public void updateUser(UserDTO userDTO) {
        User user = findUserById(userDTO.getId());
        user.setUsername(userDTO.getUsername());
        user.setUserType(UserTypes.valueOf(userDTO.getUserType().name()));
        userRepository.save(user);
    }
}
