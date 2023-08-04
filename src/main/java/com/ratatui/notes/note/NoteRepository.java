package com.ratatui.notes.note;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import com.ratatui.notes.family.Family;
import com.ratatui.notes.user.User;

import java.util.UUID;

@Repository
public interface NoteRepository extends PagingAndSortingRepository<Note, UUID>,
        JpaRepository<Note, UUID> {
    @Query("SELECT distinct n FROM Note n LEFT JOIN n.tagList t INNER JOIN n.noteOwner u WHERE (u = :noteOwner OR u.family = :family) AND (UPPER(n.title) like UPPER(CONCAT('%',:searchText,'%')) OR UPPER(t.title) like UPPER(CONCAT('%',:searchText,'%')))")
    Page<Note> findNoteList(@Param("noteOwner") User noteOwner, @Param("family") Family family, @Param("searchText") String textSearch, Pageable pageable);
}
