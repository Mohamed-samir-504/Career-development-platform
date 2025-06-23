
package org.sumerge.careerpackageservice.Entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.UUID;

import java.util.List;

@Data
@Entity
public class UserSectionResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    private UserCareerPackage userCareerPackage;

    @ManyToOne
    @JoinColumn(name = "section_template_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SectionTemplate sectionTemplate;

    @OneToMany(mappedBy = "sectionResponse", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserFieldResponse> fieldResponses;
}
