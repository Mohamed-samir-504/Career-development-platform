
package org.sumerge.careerpackageservice.Repository;

import org.sumerge.careerpackageservice.Entity.UserSectionSubmission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserSectionSubmissionRepository extends JpaRepository<UserSectionSubmission, UUID> {
}
