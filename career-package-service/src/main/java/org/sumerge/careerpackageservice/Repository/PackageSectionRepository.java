package org.sumerge.careerpackageservice.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.sumerge.careerpackageservice.Entity.PackageSection;

public interface PackageSectionRepository extends JpaRepository<PackageSection, Long> {
}
