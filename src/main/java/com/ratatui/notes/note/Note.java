package com.ratatui.notes.note;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import javax.validation.constraints.Pattern;

import java.time.Instant;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "note", schema = "notes")
public class Note {
    @Id
    @Column(name = "note_id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    @Pattern(regexp =".{5}|.{100}")
    @Column(name = "title", length = 100, nullable = false)
    private String title;
    @Pattern(regexp =".{5}|.{10000}")
    @Column(name = "content", length = 10000, nullable = false)
    private String content;
    @Column(name = "note_owner", nullable = false)
    private UUID noteOwner;
    @Column(name = "note_access_type", nullable = false)
    private String noteAccessType;
    @Column(name = "created_date")
    @CreationTimestamp
    private Instant createdDate;
    @Column(name = "updated_date")
    @UpdateTimestamp
    private Instant updatedDate;
}