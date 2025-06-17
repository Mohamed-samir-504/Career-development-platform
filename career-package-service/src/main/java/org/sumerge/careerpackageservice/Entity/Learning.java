
package org.sumerge.careerpackageservice.Entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Learning {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private UserPackageSection section;

    private String topic;
    private String provider;
    private int hours;
    private String proofUrl;
    private LocalDate date;

}
