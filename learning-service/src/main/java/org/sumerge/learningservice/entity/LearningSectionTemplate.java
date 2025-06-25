package org.sumerge.learningservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.sumerge.learningservice.enums.SectionType;

import java.util.UUID;

@Entity
@Table(name = "learning_section_templates")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LearningSectionTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String title;

    @Enumerated(EnumType.STRING)
    private SectionType type;

    @Column(columnDefinition = "TEXT")
    private String instructions;

    @Column(columnDefinition = "TEXT")
    private String content; // Read-only content (for informative sections)

    private boolean requiresSubmission;

    private String attachmentId; //reference to mongodb attachments

    @ManyToOne
    @JoinColumn(name = "learning_material_template_id")
    private LearningMaterialTemplate learningMaterialTemplate;
}

