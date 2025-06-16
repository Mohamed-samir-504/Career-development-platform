package org.sumerge.careerpackageservice.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.sumerge.careerpackageservice.Entity.CareerPackage;

public interface CareerPackageRepository extends JpaRepository<CareerPackage, Long> {
}
