package com.ratatui.notes.note;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface NoteRepository extends PagingAndSortingRepository<Note, UUID>, JpaRepository<Note, UUID> {
    List<Note> findAllByNoteOwner(UUID noteOwner);
    List<Note> findAllByNoteOwner(UUID noteOwner, Pageable pageable);

}
