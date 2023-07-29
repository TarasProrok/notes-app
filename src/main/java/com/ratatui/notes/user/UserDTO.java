package com.ratatui.notes.user;

import com.ratatui.notes.authorities.Authorities;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;

import java.sql.Date;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
public class UserDTO {
    private UUID id;
    private String email;
    private String password;
    private boolean isEnable;
    private String nickname;
    private Date birthDate;
    private int genderId;
    private Instant updatedDate;
    private Instant createdDate;
    @Enumerated(EnumType.STRING)
    private List<Authorities> authorities;
}
