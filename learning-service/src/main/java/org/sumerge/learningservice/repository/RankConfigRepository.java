package org.sumerge.learningservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.sumerge.learningservice.entity.RankConfig;

import java.util.List;

public interface RankConfigRepository extends JpaRepository<RankConfig, String> {
    List<RankConfig> findAllByOrderByPointsRequiredAsc();
}
