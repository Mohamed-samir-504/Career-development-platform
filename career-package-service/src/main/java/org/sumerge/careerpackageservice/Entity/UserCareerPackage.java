
package org.sumerge.careerpackageservice.Entity;

import jakarta.persistence.*;
import lombok.Data;
import org.sumerge.careerpackageservice.Enums.PackageStatus;

import java.util.List;

@Data
@Entity
public class UserCareerPackage {
    @Id @GeneratedValue
    private Long id;

    private Long userId;
    private Long reviewerId;

    @Enumerated(EnumType.STRING)
    private PackageStatus status;

    @ManyToOne
    private CareerPackageTemplate template;

    @OneToMany(mappedBy = "userCareerPackage", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserSectionResponse> sectionResponses;
}
