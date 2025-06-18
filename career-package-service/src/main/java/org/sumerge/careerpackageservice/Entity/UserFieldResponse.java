
package org.sumerge.careerpackageservice.Entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.UUID;

@Data
@Entity
public class UserFieldResponse {
    @Id @GeneratedValue
    private UUID id;

    @ManyToOne
    private UserSectionResponse sectionResponse;

    @ManyToOne
    private SectionFieldTemplate fieldTemplate;

    @Column(columnDefinition = "TEXT")
    private String value;
}
