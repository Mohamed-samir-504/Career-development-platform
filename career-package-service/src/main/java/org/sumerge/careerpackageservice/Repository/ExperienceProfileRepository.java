package org.sumerge.careerpackageservice.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.sumerge.careerpackageservice.Entity.ExperienceProfile;

public interface ExperienceProfileRepository extends JpaRepository<ExperienceProfile, Long> {
}
