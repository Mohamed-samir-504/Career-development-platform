package org.sumerge.learningservice.repository;

import org.sumerge.learningservice.entity.LearningFieldTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LearningFieldTemplateRepository extends JpaRepository<LearningFieldTemplate, UUID> {
}