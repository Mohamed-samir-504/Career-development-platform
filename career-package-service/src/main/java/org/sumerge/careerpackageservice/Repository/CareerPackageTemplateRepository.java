package org.sumerge.careerpackageservice.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.sumerge.careerpackageservice.Entity.CareerPackageTemplate;

public interface CareerPackageTemplateRepository extends JpaRepository<CareerPackageTemplate, Long> {
}
