
package org.sumerge.careerpackageservice.Repository;

import org.sumerge.careerpackageservice.Entity.UserCareerPackage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserCareerPackageRepository extends JpaRepository<UserCareerPackage, Long> {
}
