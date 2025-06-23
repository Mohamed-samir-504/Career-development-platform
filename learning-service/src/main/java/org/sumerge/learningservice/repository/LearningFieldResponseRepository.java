package org.sumerge.learningservice.repository;

import org.sumerge.learningservice.entity.LearningFieldResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LearningFieldResponseRepository extends JpaRepository<LearningFieldResponse, UUID> {
}
