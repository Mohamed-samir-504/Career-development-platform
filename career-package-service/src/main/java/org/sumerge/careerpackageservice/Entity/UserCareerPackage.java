
package org.sumerge.careerpackageservice.Entity;

import org.sumerge.careerpackageservice.Enums.PackageStatus;
import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
public class UserCareerPackage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long reviewerId;

    private LocalDate startDate;
    private LocalDate submissionDate;

    @Enumerated(EnumType.STRING)
    private PackageStatus status;

    @ManyToOne
    private CareerPackageTemplate template;

    @OneToMany(mappedBy = "userCareerPackage", cascade = CascadeType.ALL)
    private List<UserPackageSection> userSections;
}
