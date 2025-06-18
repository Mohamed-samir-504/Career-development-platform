
package org.sumerge.careerpackageservice.Entity;

import jakarta.persistence.*;
import lombok.Data;
import java.util.UUID;

@Data
@Entity
public class SectionFieldTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String label;
    private String fieldKey;
    private String fieldType;
    private boolean required;

    @ManyToOne
    private SectionTemplate sectionTemplate;
}
