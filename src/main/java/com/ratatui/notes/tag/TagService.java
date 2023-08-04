package com.ratatui.notes.tag;

import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
import lombok.Data;

import java.util.List;

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
        if (isNull(tag)) {
            return null;
        }
        if (!isNull(tag.getId())) {
            Tag targetTag = tagRepository.getReferenceById(tag.getId());
            if (targetTag.getTitle().equalsIgnoreCase(tag.getTitle())) {
                return tagMapper.mapEntityToDto(targetTag);
            }
        }
        List<TagDto> listTag = tagMapper.mapEntityToDto(tagRepository.findAllByTitle(tagDto.getTitle()));
        if (listTag.isEmpty()) {
            return tagMapper.mapEntityToDto(tagRepository.save(tag));
        }
        return listTag.stream().findFirst().get();
    }

    public TagDto addIfNotExists(String title) {
        TagDto tagDto = new TagDto();
        tagDto.setTitle(title);
        return addIfNotExists(tagDto);
    }

    public TagDto add(TagDto tagDto) {
        Tag tag = tagMapper.mapDtoToEntity(tagDto);
        return tagMapper.mapEntityToDto(tagRepository.save(tag));
    }
}
