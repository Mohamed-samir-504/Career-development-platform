
package org.sumerge.careerpackageservice.Entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Learning {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private PackageSection section;

    private String topic;
    private String provider;
    private int hours;
    private String proofUrl;
    private LocalDate date;

}
