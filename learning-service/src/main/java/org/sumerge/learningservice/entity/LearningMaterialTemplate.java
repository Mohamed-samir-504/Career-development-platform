package org.sumerge.learningservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "learning_material_templates")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LearningMaterialTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private int points;

    @Column(nullable = false)
    private UUID careerPackageId;

    @Column(columnDefinition = "TEXT")
    private String description;

    @OneToMany(mappedBy = "learningMaterialTemplate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LearningSectionTemplate> sections;
}
