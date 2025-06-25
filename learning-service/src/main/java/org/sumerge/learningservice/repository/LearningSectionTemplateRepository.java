package org.sumerge.learningservice.repository;

import org.sumerge.learningservice.entity.LearningSectionTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LearningSectionTemplateRepository extends JpaRepository<LearningSectionTemplate, UUID> {
}
