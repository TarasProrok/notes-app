package com.ratatui.notes.note;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private NoteRepository noteRepository;

    @Autowired
    public NoteService(NoteRepository noteRepository) {
        this.noteRepository = noteRepository;
    }

    public Note add(Note note) {
        if (note.getId() != null) {
            throw new IllegalArgumentException("Note id must be null");
        }
        return noteRepository.save(note);
    }

    public void deleteById(String id) {
        if (id == null) {
            throw new IllegalArgumentException("Note id cannot be null");
        }
        noteRepository.deleteById(id);
    }

    public void update(Note note) {
        if (note == null) {
            throw new IllegalArgumentException("Note cannot be null");
        }
        if (note.getId() == null) {
            throw new IllegalArgumentException("Note id cannot be null");
        }
        noteRepository.save(note);
    }

    public List<Note> listAll() {
        return noteRepository.findAll();
    }

    public Note getById(String id) {
        if (id == null) {
            throw new IllegalArgumentException("Note id cannot be null");
        }
        return noteRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Note not found"));
    }
}
