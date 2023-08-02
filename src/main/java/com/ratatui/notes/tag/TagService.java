package com.ratatui.notes.tag;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import static java.util.Objects.isNull;

@Data
@Service
@RequiredArgsConstructor
public class TagService {
    private final TagRepository tagRepository;
    private final TagMapper tagMapper;

    public List<TagDto> listAll() {
        return tagMapper.mapEntityToDto(tagRepository.findAll());
    }
    public TagDto addIfNotExists(TagDto tagDto) {
        Tag tag = tagMapper.mapDtoToEntity(tagDto);
        if (isNull(tag)) return null;
        if (!isNull(tag.getId())) {
            Tag targetTag = tagRepository.getReferenceById(tag.getId());
            if (targetTag.getTitle().equalsIgnoreCase(tag.getTitle())) return tagMapper.mapEntityToDto(targetTag);
        }
        List<TagDto> listTag = tagMapper.mapEntityToDto(tagRepository.findAllByTitle(tagDto.getTitle()));
        if (listTag.isEmpty()) {
            return tagMapper.mapEntityToDto(tagRepository.save(tag));
        }
        return listTag.get(0);
    }
    public TagDto addIfNotExists(String title) {
        TagDto tagDto = new TagDto();
        tagDto.setTitle(title);
        return addIfNotExists(tagDto);
    }
    public void deleteById(UUID id) {
        tagRepository.deleteById(id);
    }

    public TagDto getById(UUID id) {
        return tagMapper.mapEntityToDto(tagRepository.getReferenceById(id));
    }

    public void deleteAll() {
        tagRepository.deleteAll();
    }

    public TagDto add(TagDto tagDto) {
        Tag tag = tagMapper.mapDtoToEntity(tagDto);
        return tagMapper.mapEntityToDto(tagRepository.save(tag));
    }

}
