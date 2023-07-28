package com.ratatui.notes.note;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.util.UUID;

@Data
@Entity
@Table(name = "note", schema = "notes")
public class Note {
    @Id
    @Column(name = "note_id")
    private UUID id = UUID.randomUUID();
    @Column(name = "title")
    private String title;
    @Column(name = "content")
    private String content;
    @Column(name = "note_owner")
    private UUID noteOwner;
    @Column(name = "note_access_type")
    private String noteAccessType;
    @Column(name = "created_date")
    private Date createdDate;
    @Column(name = "updated_date")
    private Date updatedDate;
}
