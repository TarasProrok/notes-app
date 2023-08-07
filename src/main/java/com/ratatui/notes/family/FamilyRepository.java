package com.ratatui.notes.family;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FamilyRepository extends JpaRepository<Family, UUID> {

    Optional<Family> findByCode(String code);
}
