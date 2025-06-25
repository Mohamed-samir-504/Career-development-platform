
package org.sumerge.careerpackageservice.Repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.sumerge.careerpackageservice.Entity.UserCareerPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface UserCareerPackageRepository extends JpaRepository<UserCareerPackage, UUID> {

    public UserCareerPackage findByUserId(UUID userId);
}
