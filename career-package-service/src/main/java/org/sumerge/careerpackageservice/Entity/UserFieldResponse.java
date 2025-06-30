package org.sumerge.careerpackageservice.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class UserFieldResponse {

    public UserFieldResponse(SectionFieldTemplate fieldTemplate, String value) {

        this.fieldTemplate = fieldTemplate;
        this.value = value;

    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String value;

    @ManyToOne
    @JoinColumn(name = "field_template_id")
    private SectionFieldTemplate fieldTemplate;

}
