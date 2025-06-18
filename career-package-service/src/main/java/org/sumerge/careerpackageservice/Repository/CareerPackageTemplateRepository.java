
package org.sumerge.careerpackageservice.Repository;

import org.sumerge.careerpackageservice.Entity.CareerPackageTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface CareerPackageTemplateRepository extends JpaRepository<CareerPackageTemplate, UUID> {
}
