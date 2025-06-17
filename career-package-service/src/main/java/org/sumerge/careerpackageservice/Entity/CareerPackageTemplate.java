
package org.sumerge.careerpackageservice.Entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class CareerPackageTemplate {
    @Id @GeneratedValue
    private Long id;

    private String title;
    private String version;
    private String description;

    @OneToMany(mappedBy = "careerPackageTemplate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SectionTemplate> sections;
}
