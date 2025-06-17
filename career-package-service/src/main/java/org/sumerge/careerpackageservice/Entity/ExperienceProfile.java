
package org.sumerge.careerpackageservice.Entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class ExperienceProfile {
    @Id @GeneratedValue
    private Long id;

    @OneToOne
    private UserPackageSection section;

    private String projectTitle;
    private String clientName;
    private LocalDate startDate;
    private LocalDate endDate;
    private Boolean acceptedByClient;

    @Column(columnDefinition = "TEXT")
    private String roleSummary;
    private String solution;
    private String outcome;
    private String lessonsLearned;

}
