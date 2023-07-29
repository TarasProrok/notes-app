package com.ratatui.notes.note;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Data
@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;

    public List<Note> listAll() {
        return noteRepository.findAll();
    }

    public List<Note> findAllByNoteOwner(UUID noteOwner) {
        return noteRepository.findAllByNoteOwner(noteOwner);
    }

    public Note add(Note note) {
        return noteRepository.save(note);
    }

    public void deleteById(UUID id) {
        noteRepository.deleteById(id);
    }

    public void update(Note note) {
        noteRepository.save(note);
    }

    public Note getById(UUID id) {
        return noteRepository.getReferenceById(id);
    }

    public void deleteAll() {
        noteRepository.deleteAll();
    }
}
