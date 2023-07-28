package com.ratatui.notes.user;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.sql.Date;
import java.util.UUID;

@Data
public class UserDTO {
    private UUID id;
    private String username;
    private String password;
    private boolean isEnable;
    private String nickname;
    private Date birthDate;
    private UUID genderId;
    private Date updatedDate;
    //    @Column(name = "user_type") - UserTypes або Authorities
    @Enumerated(EnumType.STRING)
    private UserTypes userType;
}
