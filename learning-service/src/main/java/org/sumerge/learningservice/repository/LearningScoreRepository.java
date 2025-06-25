package org.sumerge.learningservice.repository;

import org.sumerge.learningservice.entity.LearningScore;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LearningScoreRepository extends JpaRepository<LearningScore, Long> {

    List<LearningScore> findTop10ByOrderByPointsDesc();
    Optional<LearningScore> findByUserId(UUID userId);
}
