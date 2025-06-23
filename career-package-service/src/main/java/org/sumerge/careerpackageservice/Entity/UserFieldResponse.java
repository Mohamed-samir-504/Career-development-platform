
package org.sumerge.careerpackageservice.Entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.UUID;

@Data
@Entity
public class UserFieldResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    private UserSectionResponse sectionResponse;

    @ManyToOne
    @JoinColumn(name = "field_template_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE) // Hibernate-level cascade
    private SectionFieldTemplate fieldTemplate;

    @Column(columnDefinition = "TEXT")
    private String value;
}
