package com.ratatui.notes.authorities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthoritiesDto {
    private int id;
    private UUID userId;
    private String authority;
}
