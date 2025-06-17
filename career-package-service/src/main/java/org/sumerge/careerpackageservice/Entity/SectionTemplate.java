
package org.sumerge.careerpackageservice.Entity;

import jakarta.persistence.*;
import lombok.Data;
import org.sumerge.careerpackageservice.Enums.SectionType;

import java.util.List;

@Data
@Entity
public class SectionTemplate {
    @Id @GeneratedValue
    private Long id;

    private String title;

    @Enumerated(EnumType.STRING)
    private SectionType type;

    @Column(columnDefinition = "TEXT")
    private String instructions;
    @Column(columnDefinition = "TEXT")
    private String requirements;

    @ManyToOne
    private CareerPackageTemplate careerPackageTemplate;

    @OneToMany(mappedBy = "sectionTemplate", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SectionFieldTemplate> fields;
}
