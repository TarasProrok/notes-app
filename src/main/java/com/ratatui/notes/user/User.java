package com.ratatui.notes.user;


import com.ratatui.notes.authorities.Authorities;
import com.ratatui.notes.note.Note;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Date;
import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Data
@Entity
@Table(name = "users", schema = "access")
public class User {
        @Id
        @Column(name = "user_id")
        @GeneratedValue(strategy = GenerationType.UUID)
        private UUID userId;
        @Column(name = "username")
        private String email;
        @Column(name = "password")
        private String password;
        @Column(name = "enabled")
        private boolean isEnable;
        @Column(name="nickname")
        private String nickname;
        @Column(name="birthday")
        private Date birthDate;
        @Column(name="gender_id")
        private int genderId;
        @Column(name = "created_date")
        @CreationTimestamp
        private Instant createdDate;
        @Column(name = "updated_date")
        @UpdateTimestamp
        private Instant updatedDate;
        @OneToMany(fetch = FetchType.LAZY, mappedBy="noteOwner")
        private List<Note> notes;
        @OneToMany(fetch = FetchType.LAZY, mappedBy="user")
        private List<Authorities> authorities;
    }
