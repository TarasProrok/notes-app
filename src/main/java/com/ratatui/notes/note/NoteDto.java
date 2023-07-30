package com.ratatui.notes.note;

import com.ratatui.notes.user.User;
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
    private User noteOwner;
    private String noteAccessType;
    private Instant createdDate;
    private Instant updatedDate;
}
