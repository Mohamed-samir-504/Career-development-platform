package org.sumerge.learningservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
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
    private SectionType type; // e.g., TEXT, FILE_UPLOAD, VIDEO, etc.

    @Column(columnDefinition = "TEXT")
    private String instructions;

    @Column(columnDefinition = "TEXT")
    private String content; // Read-only content (for informative sections)

    private boolean requiresSubmission;

    @ManyToOne
    @JoinColumn(name = "learning_material_template_id")
    private LearningMaterialTemplate learningMaterialTemplate;

    @OneToMany(mappedBy = "sectionTemplate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LearningFieldTemplate> fields;
}

