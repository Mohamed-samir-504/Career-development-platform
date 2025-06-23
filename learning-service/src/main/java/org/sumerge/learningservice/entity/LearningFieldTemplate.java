package org.sumerge.learningservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "learning_field_templates")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LearningFieldTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String label;       // e.g. "Upload your document"
    private String fieldKey;    // Used as a reference key
    private String fieldType;   // e.g. "text", "file", "checkbox"

    private boolean required;

    @ManyToOne
    @JoinColumn(name = "section_template_id")
    private LearningSectionTemplate sectionTemplate;
}
