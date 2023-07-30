package com.ratatui.notes.note;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
@Data
@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;
    private final NoteMapper noteMapper;

    public List<NoteDto> listAll() {
        return noteMapper.mapEntityToDto(noteRepository.findAll());
    }

    public List<NoteDto> findAllByNoteOwner(UUID noteOwner) {
        return noteMapper.mapEntityToDto(noteRepository.findAllByNoteOwner(noteOwner));
    }

    public NoteDto add(NoteDto noteDto) {
        return noteMapper.mapEntityToDto(noteRepository.save(noteMapper.mapDtoToEntity(noteDto)));
    }

    public void deleteById(UUID id) {
        noteRepository.deleteById(id);
    }

    public void update(NoteDto noteDto) {
        noteRepository.save(noteMapper.mapDtoToEntity(noteDto));
    }

    public NoteDto getById(UUID id) {
        return noteMapper.mapEntityToDto(noteRepository.getReferenceById(id));
    }
    public void deleteAll() { noteRepository.deleteAll();}
}
