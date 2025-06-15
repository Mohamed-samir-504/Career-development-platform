
package org.sumerge.careerpackageservice.Entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class SkillEvidence {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private PackageSection section;

    private String skillName;
    private String skillCategory;

    @Column(columnDefinition = "TEXT")
    private String situation;

    @Column(columnDefinition = "TEXT")
    private String action;

    private String proofUrl;
    private LocalDate date;

}
