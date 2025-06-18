
package org.sumerge.careerpackageservice.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Data
@Entity
public class CareerPackageTemplate {
    @Id @GeneratedValue
    private UUID id;

    private String title;
    private String version;
    private String description;

    @OneToMany(mappedBy = "careerPackageTemplate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SectionTemplate> sections;
}
