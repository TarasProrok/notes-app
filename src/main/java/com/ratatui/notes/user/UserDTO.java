package com.ratatui.notes.user;

import com.ratatui.notes.family.FamilyResponseDto;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.time.Instant;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
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
    private FamilyResponseDto family;
    private UserRoles role;
    private boolean fullWidth;
}
