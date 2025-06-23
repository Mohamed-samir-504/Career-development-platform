package org.sumerge.learningservice.repository;

import org.sumerge.learningservice.entity.LearningMaterialTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LearningMaterialTemplateRepository extends JpaRepository<LearningMaterialTemplate, UUID> {
}
