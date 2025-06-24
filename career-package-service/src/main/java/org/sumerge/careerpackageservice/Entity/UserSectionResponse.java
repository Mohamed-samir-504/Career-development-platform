package org.sumerge.careerpackageservice.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Entity
public class UserSectionResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_career_package_id")
    @JsonIgnore // 🔁 Prevents recursion
    private UserCareerPackage userCareerPackage;

    @ManyToOne
    @JoinColumn(name = "section_template_id")
    private SectionTemplate sectionTemplate;

    @OneToMany(mappedBy = "sectionResponse", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserFieldResponse> fieldResponses;
}
