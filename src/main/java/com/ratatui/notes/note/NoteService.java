package com.ratatui.notes.note;

import com.ratatui.notes.tag.Tag;
import com.ratatui.notes.tag.TagDto;
import com.ratatui.notes.tag.TagMapper;
import com.ratatui.notes.tag.TagService;
import com.ratatui.notes.user.User;
import com.ratatui.notes.user.UserService;
import com.ratatui.notes.utils.Helper;
import jakarta.persistence.EntityNotFoundException;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.util.UriComponentsBuilder;

import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Data
@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;
    private final NoteMapper noteMapper;
    private final UserService userService;
    private final TagService tagService;
    private final TagMapper tagMapper;

    public Page<NoteDto> findAll(Pageable pageable) {
        return noteRepository.findAll(pageable).map(this::convertToObjectDto);
    }

    public Page<NoteDto> findAllByNoteOwnerFamily(Pageable pageable) {
        User currentUser = userService.getCurrentUser();
        if (currentUser.getFamily() == null) {
            return noteRepository.findAllByNoteOwner(currentUser, pageable).map(this::convertToObjectDto);
        } else {
            return noteRepository.findAllByNoteOwnerFamily(currentUser.getFamily(), pageable).map(this::convertToObjectDto);
        }
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
        Note note = noteMapper.mapDtoToEntity(noteDto);
        Note savedNote = noteRepository.save(note);
        return noteMapper.mapEntityToDto(savedNote);
    }

    public void deleteById(UUID id) {
        noteRepository.deleteById(id);
    }

    public void update(NoteDto noteDto) {
        NoteDto dto = getById(noteDto.getId());
        BeanUtils.copyProperties(noteDto, dto, Helper.getNullPropertyNames(noteDto));

        noteRepository.save(noteMapper.mapDtoToEntity(dto));
    }

    public NoteDto getById(UUID id) throws EntityNotFoundException {
        Note note = noteRepository.getReferenceById(id);
        return noteMapper.mapEntityToDto(note);
    }

    public void deleteAll() {
        noteRepository.deleteAll();
    }

    public void copyLink(String url) {
        StringSelection stringSelection = new StringSelection(url);
        System.setProperty("java.awt.headless", "false");
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
    }

    public NoteDto addTag(UUID noteId, String tagTitle) {
        TagDto tagDto = tagService.addIfNotExists(tagTitle);
        NoteDto noteDto = getById(noteId);
        List<Tag> tagList = noteDto.getTagList();
        String tagDtoTitle = tagDto.getTitle();
        if (tagList.stream().filter(tag -> tag.getTitle().equalsIgnoreCase(tagDtoTitle)).count() == 0 && !tagDtoTitle.isBlank()) {
            tagList.add(tagMapper.mapDtoToEntity(tagDto));
            update(noteDto);
        }
        return noteDto;
    }

    public NoteDto deleteTag(UUID noteId, UUID tagID) {
        NoteDto noteDto = getById(noteId);
        List<TagDto> tagList = tagMapper.mapEntityToDto(noteDto.getTagList());
        List<TagDto> newTagList = tagList.stream().filter(tag -> !tagID.equals(tag.getId())).collect(Collectors.toList());
        noteDto.setTagList(tagMapper.mapDtoToEntity(newTagList));
        update(noteDto);
        return noteDto;
    }
    public String getSharedLink(UUID noteId, UriComponentsBuilder uriComponentsBuilder)  {
        return uriComponentsBuilder.replacePath(null).replaceQuery(null).build().toString() + "/note/share/" + noteId;
    }
}
