
package org.sumerge.careerpackageservice.Entity;

import jakarta.persistence.*;

@Entity
public class UserFieldResponse {
    @Id @GeneratedValue
    private Long id;

    @ManyToOne
    private UserSectionResponse sectionResponse;

    @ManyToOne
    private SectionFieldTemplate fieldTemplate;

    @Column(columnDefinition = "TEXT")
    private String value;
}
