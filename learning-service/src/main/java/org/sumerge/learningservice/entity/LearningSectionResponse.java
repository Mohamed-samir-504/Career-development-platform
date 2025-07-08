package org.sumerge.learningservice.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
    @OnDelete(action = OnDeleteAction.CASCADE)
    private LearningSubmission learningSubmission;

    @ManyToOne
    @JoinColumn(name = "section_template_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private LearningSectionTemplate sectionTemplate;


    @Column(columnDefinition = "TEXT")
    private String userInput;

    private String documentId;
}
