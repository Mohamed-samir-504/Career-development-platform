
package org.sumerge.careerpackageservice.Entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class ExperienceProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private PackageSection section;

    private String projectTitle;
    private String clientName;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean acceptedByClient;

    @Column(columnDefinition = "TEXT")
    private String roleSummary;

    @Column(columnDefinition = "TEXT")
    private String solution;

    @Column(columnDefinition = "TEXT")
    private String outcome;

    @Column(columnDefinition = "TEXT")
    private String lessonsLearned;

}
