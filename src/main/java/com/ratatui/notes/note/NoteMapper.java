package com.ratatui.notes.note;

import com.ratatui.notes.mapper.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Objects.isNull;
@Component
public class NoteMapper implements Mapper<Note, NoteDto> {
    @Override
    public NoteDto mapEntityToDto(Note source) throws RuntimeException {
        if (isNull(source)) {
            return null;
        }
        NoteDto target = new NoteDto();
        target.setId(source.getId());
        target.setTitle(source.getTitle());
        target.setContent(source.getContent());
        target.setNoteOwner(source.getNoteOwner());
        target.setNoteAccessType(source.getNoteAccessType());
        target.setCreatedDate(source.getCreatedDate());
        target.setUpdatedDate(source.getUpdatedDate());
        target.setTagList(source.getTagList());
        return target;
    }

    @Override
    public Note mapDtoToEntity(NoteDto source) throws RuntimeException {
        if (isNull(source)) {
            return null;
        }
        Note target = new Note();
        target.setId(source.getId());
        target.setTitle(source.getTitle());
        target.setContent(source.getContent());
        target.setNoteOwner(source.getNoteOwner());
        target.setNoteAccessType(source.getNoteAccessType());
        target.setCreatedDate(source.getCreatedDate());
        target.setUpdatedDate(source.getUpdatedDate());
        target.setTagList(source.getTagList());
        return target;
    }
    @Override
    public List<NoteDto> mapEntityToDto(List<Note> source) throws RuntimeException {
        return Mapper.super.mapEntityToDto(source);
    }
    @Override
    public List<Note> mapDtoToEntity(List<NoteDto> source) throws RuntimeException {
        return Mapper.super.mapDtoToEntity(source);
    }
}
