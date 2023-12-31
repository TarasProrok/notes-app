package com.ratatui.notes.note;

import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.beans.BeanUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.Data;

import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Clipboard;
import java.awt.Toolkit;
import java.util.stream.Collectors;
import java.util.List;
import java.util.UUID;


import com.ratatui.notes.user.UserService;
import com.ratatui.notes.tag.TagService;
import com.ratatui.notes.tag.TagMapper;
import com.ratatui.notes.utils.Helper;
import com.ratatui.notes.tag.TagDto;
import com.ratatui.notes.user.User;
import com.ratatui.notes.tag.Tag;

@Data
@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;
    private final NoteMapper noteMapper;
    private final UserService userService;
    private final TagService tagService;
    private final TagMapper tagMapper;
    private final NoteValidator noteValidator;

    public Page<NoteDto> findAllByNoteOwnerFamily(Pageable pageable, String searchText) {
        User currentUser = userService.getCurrentUser();
        return noteRepository.findNoteList(currentUser, currentUser.getFamily(), searchText, pageable).map(this::convertToObjectDto);
    }

    private NoteDto convertToObjectDto(Note note) {
        return noteMapper.mapEntityToDto(note);
    }

    public NoteDto add(NoteDto noteDto) {
        noteDto.setNoteOwner(userService.getCurrentUser());
        Note note = noteMapper.mapDtoToEntity(noteDto);
        noteValidator.validate(noteDto);
        Note savedNote = noteRepository.save(note);
        return noteMapper.mapEntityToDto(savedNote);
    }

    public void deleteById(UUID id) {
        noteRepository.deleteById(id);
    }

    public void update(NoteDto noteDto) {
        NoteDto dto = getById(noteDto.getId());
        BeanUtils.copyProperties(noteDto, dto, Helper.getNullPropertyNames(noteDto));
        noteValidator.validate(noteDto);
        noteRepository.save(noteMapper.mapDtoToEntity(dto));
    }

    public NoteDto getById(UUID id) throws EntityNotFoundException {
        Note note = noteRepository.getReferenceById(id);
        return noteMapper.mapEntityToDto(note);
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

    public String getSharedLink(UUID noteId, UriComponentsBuilder uriComponentsBuilder) {
        return uriComponentsBuilder.replacePath(null).replaceQuery(null).build().toString() + "/note/share/" + noteId;
    }
}
