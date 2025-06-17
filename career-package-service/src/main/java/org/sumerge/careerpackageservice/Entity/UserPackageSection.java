
package org.sumerge.careerpackageservice.Entity;

import jakarta.persistence.*;

@Entity
public class UserPackageSection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private UserCareerPackage userCareerPackage;

    @ManyToOne
    private PackageSectionTemplate template;
}
