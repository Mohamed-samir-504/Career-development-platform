package org.sumerge.learningservice.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "learning_section_responses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LearningSectionResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "submission_id")
    private LearningSubmission learningSubmission;

    @ManyToOne
    @JoinColumn(name = "section_template_id")
    private LearningSectionTemplate sectionTemplate;

    @OneToMany(mappedBy = "sectionResponse", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<LearningFieldResponse> fieldResponses;
}
