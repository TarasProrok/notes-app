package com.ratatui.notes.family;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

/**
 * @author Andriy Gaponov
 */
@Data
@Builder
public class FamilyResponseDto {

    private UUID id;
    private String title;
    private String code;
}
