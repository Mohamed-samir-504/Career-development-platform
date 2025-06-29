
package org.sumerge.careerpackageservice.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class CareerPackageTemplate {

    public CareerPackageTemplate(String title,String description,List<SectionTemplate> sections) {

        this.title = title;
        this.description = description;
        this.sections = sections;
    }
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String title;
    private String description;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "career_package_template_id") // FK in SectionTemplate
    private List<SectionTemplate> sections;
}
