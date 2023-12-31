package com.ratatui.notes.note;

import com.ratatui.notes.tag.Tag;
import com.ratatui.notes.user.User;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
    @Column(name = "title", length = 100, nullable = false)
    private String title;
    @Column(name = "content", length = 10000, nullable = false)
    private String content;
    @ManyToOne
    @JoinColumn(name = "note_owner", nullable = false)
    private User noteOwner;
    @Enumerated(EnumType.STRING)
    @Column(name = "note_access_type", nullable = false)
    private NoteAccessType noteAccessType;
    @Column(name = "created_date")
    @CreationTimestamp
    private Instant createdDate;
    @Column(name = "updated_date")
    @UpdateTimestamp
    private Instant updatedDate;
    @JoinTable(
        name = "note_tag",
        schema = "notes",
        joinColumns = @JoinColumn(name = "note_id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    @ManyToMany
    private List<Tag> tagList;
}