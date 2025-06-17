package org.sumerge.careerpackageservice.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.sumerge.careerpackageservice.Entity.UserCareerPackage;

public interface UserCareerPackageRepository extends JpaRepository<UserCareerPackage, Long> {
}
