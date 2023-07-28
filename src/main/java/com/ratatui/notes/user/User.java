package com.ratatui.notes.user;


import com.ratatui.notes.note.Note;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "users", schema = "access")
public class User {
        @Id
        @Column(name = "user_id")
        private UUID userId = UUID.randomUUID();
        @Column(name = "username")
        private String username;
        @Column(name = "password")
        private String password;
        @Column(name = "enabled")
        private boolean isEnable;
        @Column(name="nickname")
        private String nickname;
        @Column(name="birthday")
        private Date birthDate;
        @Column(name="gender_id")
        private UUID genderId;
        @Column(name="created_date")
        private Date createdDate;
        @Column(name="updated_date")
        private Date updatedDate;
        @OneToMany (mappedBy = "note_owner", cascade = CascadeType.REMOVE)
        @ToString.Exclude
        private List<Note> notes;
//    @Column(name = "user_type") - UserTypes або Authorities
        private UserTypes userType;
    }
