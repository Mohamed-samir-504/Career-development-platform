
package org.sumerge.careerpackageservice.Repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.sumerge.careerpackageservice.Entity.UserCareerPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.sumerge.careerpackageservice.Enums.PackageStatus;

import java.util.UUID;

public interface UserCareerPackageRepository extends JpaRepository<UserCareerPackage, UUID> {

    public UserCareerPackage findByUserId(UUID userId);
    public UserCareerPackage findByUserIdAndStatus(UUID userId, PackageStatus status);
}
