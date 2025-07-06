package org.sumerge.careerpackageservice.Entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;
import java.util.UUID;

@Data
@Entity
public class UserSectionSubmission {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;


    @ManyToOne
    @JoinColumn(name = "section_template_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private SectionTemplate sectionTemplate;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "section_response_id")
    private List<UserFieldSubmission> fieldResponses;
}
