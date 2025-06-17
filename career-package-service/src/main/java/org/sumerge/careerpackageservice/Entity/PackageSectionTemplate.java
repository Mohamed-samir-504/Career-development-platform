
package org.sumerge.careerpackageservice.Entity;

import org.sumerge.careerpackageservice.Enums.SectionType;
import jakarta.persistence.*;

@Entity
public class PackageSectionTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    @Enumerated(EnumType.STRING)
    private SectionType type;

    private int orderIndex;

    @ManyToOne
    private CareerPackageTemplate template;
}
