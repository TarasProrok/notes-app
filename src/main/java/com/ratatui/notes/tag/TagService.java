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

    public List<Tag> listAll() {
        return tagRepository.findAll();
    }

    public Tag addIfNotExists(Tag tag) {
        if (isNull(tag)) return null;
        if (!isNull(tag.getId())) {
            Tag targetTag = tagRepository.getReferenceById(tag.getId());
            if (targetTag.getTitle().equalsIgnoreCase(tag.getTitle())) return targetTag;
        }
        List<Tag> listTag = tagRepository.findAllByTitle(tag.getTitle());
        if (listTag.isEmpty()) {
            return tagRepository.save(tag);
        }
        return listTag.get(0);
    }

    public void deleteById(UUID id) {
        tagRepository.deleteById(id);
    }

    public Tag getById(UUID id) {
        return tagRepository.getReferenceById(id);
    }

    public void deleteAll() {
        tagRepository.deleteAll();
    }
    public Tag add(Tag tag) {
        return tagRepository.save(tag);
    }

}
