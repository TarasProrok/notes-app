package com.ratatui.notes.family;

import com.ratatui.notes.mapper.Mapper;
import com.ratatui.notes.note.Note;
import com.ratatui.notes.note.NoteDto;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Objects.isNull;

@Component
public class FamilyMapper implements Mapper<Family, FamilyResponseDto> {
    @Override
    public FamilyResponseDto mapEntityToDto(Family source) throws RuntimeException {
        if (isNull(source)) {
            return null;
        }

        return FamilyResponseDto.builder()
                .title(source.getTitle())
                .id(source.getId())
                .code(source.getCode())
                .build();
    }

    @Override
    public Family mapDtoToEntity(FamilyResponseDto source) throws RuntimeException {
        if (isNull(source)) {
            return null;
        }
        return Family.builder()
                .title(source.getTitle())
                .id(source.getId())
                .code(source.getCode())
                .build();
    }
    @Override
    public List<FamilyResponseDto> mapEntityToDto(List<Family> source) throws RuntimeException {
        return Mapper.super.mapEntityToDto(source);
    }
    @Override
    public List<Family> mapDtoToEntity(List<FamilyResponseDto> source) throws RuntimeException {
        return Mapper.super.mapDtoToEntity(source);
    }
}
