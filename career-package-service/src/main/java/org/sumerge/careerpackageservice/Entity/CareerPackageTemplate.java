
package org.sumerge.careerpackageservice.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Entity
public class CareerPackageTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String title;
    private String version;
    private String description;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "career_package_template_id") // FK in SectionTemplate
    private List<SectionTemplate> sections;
}
