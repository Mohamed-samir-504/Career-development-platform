
package org.sumerge.careerpackageservice.Entity;


import jakarta.persistence.*;
import org.sumerge.careerpackageservice.Enums.SectionType;

@Entity
public class PackageSection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private CareerPackage careerPackage;

    @Enumerated(EnumType.STRING)
    private SectionType type;

    private int orderIndex;
    private String title;

}
