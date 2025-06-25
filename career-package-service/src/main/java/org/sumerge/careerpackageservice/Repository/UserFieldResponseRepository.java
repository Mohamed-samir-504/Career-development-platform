
package org.sumerge.careerpackageservice.Repository;

import org.sumerge.careerpackageservice.Entity.UserFieldResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface UserFieldResponseRepository extends JpaRepository<UserFieldResponse, UUID> {
}
