package com.ratatui.notes.family;

import static java.util.Objects.isNull;

import com.ratatui.notes.mapper.Mapper;
import java.util.List;
import org.springframework.stereotype.Component;

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
