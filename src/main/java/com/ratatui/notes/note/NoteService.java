package com.ratatui.notes.note;
import com.ratatui.notes.user.UserService;
import com.ratatui.notes.utils.Helper;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.Objects;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;
@Data
@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;
    private final NoteMapper noteMapper;
    private final UserService userService;

    public Page<NoteDto> findAll(Pageable pageable) {
        return noteRepository.findAll(pageable).map(this::convertToObjectDto);
    }

    public NoteDto convertToObjectDto(Note note) {
        return noteMapper.mapEntityToDto(note);
    }

    public List<NoteDto> listAll() {
        return noteMapper.mapEntityToDto(noteRepository.findAll());
    }

    public List<NoteDto> findAllByNoteOwner(UUID noteOwner) {
        return noteMapper.mapEntityToDto(noteRepository.findAllByNoteOwner(noteOwner));
    }

    public NoteDto add(NoteDto noteDto) {
        noteDto.setNoteOwner(userService.getCurrentUser());
        return noteMapper.mapEntityToDto(noteRepository.save(noteMapper.mapDtoToEntity(noteDto)));
    }

    public void deleteById(UUID id) {
        noteRepository.deleteById(id);
    }

    public void update(NoteDto noteDto) {
        NoteDto dto = getById(noteDto.getId());
        BeanUtils.copyProperties(noteDto, dto, Helper.getNullPropertyNames(noteDto));

        noteRepository.save(noteMapper.mapDtoToEntity(dto));
    }

    public NoteDto getById(UUID id) {
        return noteMapper.mapEntityToDto(noteRepository.getReferenceById(id));
    }
    public void deleteAll() { noteRepository.deleteAll();}

    public void copyLink(String url) {
        StringSelection stringSelection = new StringSelection(url);
        System.setProperty("java.awt.headless", "false");
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }
}
