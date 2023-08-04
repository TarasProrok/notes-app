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
    @Query("SELECT distinct n FROM Note n LEFT JOIN n.tagList t INNER JOIN n.noteOwner u WHERE (u = :noteOwner OR u.family = :family) AND (UPPER(n.title) like UPPER(CONCAT('%',:searchText,'%')) OR UPPER(t.title) like UPPER(CONCAT('%',:searchText,'%')))" )
    Page<Note> findNoteList(@Param("noteOwner") User noteOwner, @Param("family") Family family, @Param("searchText") String textSearch, Pageable pageable);
}
