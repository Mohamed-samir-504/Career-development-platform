package org.sumerge.careerpackageservice.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.sumerge.careerpackageservice.Entity.UserPackageSection;

public interface UserPackageSectionRepository extends JpaRepository<UserPackageSection, Long> {
}
