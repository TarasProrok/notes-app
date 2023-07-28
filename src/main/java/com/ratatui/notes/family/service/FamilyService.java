package com.ratatui.notes.family.service;

import com.ratatui.notes.family.entity.Family;
import com.ratatui.notes.family.repository.FamilyRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Andriy Gaponov
 */
@Slf4j
@RequiredArgsConstructor
@Service
public class FamilyService {

    private final FamilyRepository familyRepository;

    public Family getFamilyByCode(String code){
        Optional<Family> optional = familyRepository.findByCode(code);
        if (optional.isEmpty()) {
            throw new IllegalArgumentException("Family not found");
        }
        return optional.get();
    }

    public Family getFamilyById(UUID id){
        Optional<Family> optional = familyRepository.findById(id);
        if (optional.isEmpty()) {
            throw new IllegalArgumentException("Family not found");
        }
        return optional.get();
    }


}
