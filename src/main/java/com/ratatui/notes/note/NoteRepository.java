package com.ratatui.notes.note;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NoteRepository extends JpaRepository<Note, UUID> {
    @Query("SELECT u FROM Note u WHERE u.noteOwner = :noteOwner")
    List<Note> findAllByNoteOwner(UUID noteOwner);
}
