
package org.sumerge.careerpackageservice.Repository;

import org.sumerge.careerpackageservice.Entity.SectionTemplate;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface SectionTemplateRepository extends JpaRepository<SectionTemplate, UUID> {
}
