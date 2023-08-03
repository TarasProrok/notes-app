package com.ratatui.notes.note;

import com.ratatui.notes.family.Family;
import com.ratatui.notes.user.User;
import java.util.List;
import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends PagingAndSortingRepository<Note, UUID>,
    JpaRepository<Note, UUID> {

    List<Note> findAllByNoteOwner(UUID noteOwner);

    List<Note> findAllByNoteOwner(UUID noteOwner, Pageable pageable);

    Page<Note> findAllByNoteOwnerFamily(Family family, Pageable pageable);

    Page<Note> findAllByNoteOwner(User noteOwner, Pageable pageable);
}
