package org.sumerge.learningservice.repository;

import org.sumerge.learningservice.entity.LearningSectionResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LearningSectionResponseRepository extends JpaRepository<LearningSectionResponse, UUID> {
}
