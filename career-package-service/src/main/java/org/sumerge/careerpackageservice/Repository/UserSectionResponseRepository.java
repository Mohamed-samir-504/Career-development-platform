
package org.sumerge.careerpackageservice.Repository;

import org.sumerge.careerpackageservice.Entity.UserSectionResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface UserSectionResponseRepository extends JpaRepository<UserSectionResponse, UUID> {
}
