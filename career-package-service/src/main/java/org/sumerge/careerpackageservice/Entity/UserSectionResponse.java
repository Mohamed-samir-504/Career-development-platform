
package org.sumerge.careerpackageservice.Entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.UUID;

import java.util.List;

@Data
@Entity
public class UserSectionResponse {
    @Id @GeneratedValue
    private UUID id;

    @ManyToOne
    private UserCareerPackage userCareerPackage;

    @ManyToOne
    private SectionTemplate sectionTemplate;

    @OneToMany(mappedBy = "sectionResponse", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserFieldResponse> fieldResponses;
}
