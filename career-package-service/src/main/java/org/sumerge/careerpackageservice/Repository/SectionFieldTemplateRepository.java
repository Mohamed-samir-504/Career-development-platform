
package org.sumerge.careerpackageservice.Repository;

import org.sumerge.careerpackageservice.Entity.SectionFieldTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface SectionFieldTemplateRepository extends JpaRepository<SectionFieldTemplate, UUID> {
}
