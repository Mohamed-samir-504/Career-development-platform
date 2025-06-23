package org.sumerge.learningservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "learning_field_responses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LearningFieldResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "section_response_id")
    private LearningSectionResponse sectionResponse;

    @ManyToOne
    @JoinColumn(name = "field_template_id")
    private LearningFieldTemplate fieldTemplate;

    @Column(nullable=true)
    private String documentId;  // reference to LearningDocument MongoDB

    @Column(columnDefinition = "TEXT")
    private String value;
}
