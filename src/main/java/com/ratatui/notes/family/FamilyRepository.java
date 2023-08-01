package com.ratatui.notes.family;

import com.ratatui.notes.family.Family;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

/**
 * @author Andriy Gaponov
 */
public interface FamilyRepository extends JpaRepository<Family, UUID> {

    Optional<Family> findByCode(String code);
}
