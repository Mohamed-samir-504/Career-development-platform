
package org.sumerge.careerpackageservice.Entity;


import jakarta.persistence.*;
import org.sumerge.careerpackageservice.Enums.ContributionType;

import java.time.LocalDate;

@Entity
public class Contribution {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private PackageSection section;

    @Enumerated(EnumType.STRING)
    private ContributionType type;

    private String topic;
    private String proofUrl;
    private LocalDate date;

    @Column(columnDefinition = "TEXT")
    private String notes;

}
