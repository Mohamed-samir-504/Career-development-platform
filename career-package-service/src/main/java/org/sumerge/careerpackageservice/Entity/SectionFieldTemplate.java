
package org.sumerge.careerpackageservice.Entity;

import jakarta.persistence.*;

@Entity
public class SectionFieldTemplate {
    @Id @GeneratedValue
    private Long id;

    private String label;
    private String fieldKey;
    private String fieldType;
    private boolean required;

    @ManyToOne
    private SectionTemplate sectionTemplate;
}
