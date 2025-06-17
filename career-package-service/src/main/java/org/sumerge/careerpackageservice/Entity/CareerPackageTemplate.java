
package org.sumerge.careerpackageservice.Entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class CareerPackageTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String version;
    private String description;
    private LocalDate createdDate;

    @OneToMany(mappedBy = "template", cascade = CascadeType.ALL)
    private List<PackageSectionTemplate> sections;
}
