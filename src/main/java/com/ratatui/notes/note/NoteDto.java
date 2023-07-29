package com.ratatui.notes.note;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NoteDto {
    private UUID id;
    private String title;
    private String content;
    private UUID noteOwner;
    private String noteAccessType;
    private Instant createdDate;
    private Instant updatedDate;
}
