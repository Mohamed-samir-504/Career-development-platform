
package org.sumerge.careerpackageservice.Entity;


import jakarta.persistence.*;
import org.sumerge.careerpackageservice.Enums.PackageStatus;

import java.time.LocalDate;
import java.util.List;

@Entity
public class CareerPackage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private LocalDate submissionDate;

    @Enumerated(EnumType.STRING)
    private PackageStatus status;

    private Long reviewedById;

    @OneToMany(mappedBy = "careerPackage", cascade = CascadeType.ALL)
    private List<PackageSection> sections;

}
