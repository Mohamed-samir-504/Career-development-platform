package org.sumerge.careerpackageservice.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class UserFieldResponse {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String value;

    @ManyToOne
    @JoinColumn(name = "field_template_id")
    private SectionFieldTemplate fieldTemplate;

    @ManyToOne
    @JoinColumn(name = "section_response_id")
    @JsonIgnore // 🔁 Prevents infinite recursion
    private UserSectionResponse sectionResponse;
}
