package org.sumerge.careerpackageservice.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.sumerge.careerpackageservice.Entity.Learning;

public interface LearningRepository extends JpaRepository<Learning, Long> {
}
