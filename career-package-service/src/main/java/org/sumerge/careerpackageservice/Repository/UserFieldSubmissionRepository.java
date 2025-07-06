
package org.sumerge.careerpackageservice.Repository;

import org.sumerge.careerpackageservice.Entity.UserFieldSubmission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserFieldSubmissionRepository extends JpaRepository<UserFieldSubmission, UUID> {
}
