
package org.sumerge.careerpackageservice.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.sumerge.careerpackageservice.Enums.SectionType;
import java.util.UUID;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class SectionTemplate {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String title;

    @Enumerated(EnumType.STRING)
    private SectionType type;

    @Column(columnDefinition = "TEXT")
    private String instructions;
    @Column(columnDefinition = "TEXT")
    private String requirements;



    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "section_template_id") // FK in SectionFieldTemplate
    private List<SectionFieldTemplate> fields;
}
