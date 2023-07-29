package com.ratatui.notes.tag;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.UUID;

@Repository
public interface TagRepository extends JpaRepository<Tag, UUID> {

    @Query("SELECT u FROM Tag u WHERE UPPER(u.title) = UPPER(:title)")
    List<Tag> findAllByTitle(String title);

}
