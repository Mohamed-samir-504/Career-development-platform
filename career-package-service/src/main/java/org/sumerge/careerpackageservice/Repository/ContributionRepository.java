package org.sumerge.careerpackageservice.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.sumerge.careerpackageservice.Entity.Contribution;

public interface ContributionRepository extends JpaRepository<Contribution, Long> {
}
