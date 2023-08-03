package com.ratatui.notes.registration;

import com.ratatui.notes.authorities.Authorities;
import com.ratatui.notes.family.FamilyResponseDto;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;

import java.sql.Date;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
public record RegistrationRequest(
            String email,
            String password,
            String nickname,
            String role) {

}
