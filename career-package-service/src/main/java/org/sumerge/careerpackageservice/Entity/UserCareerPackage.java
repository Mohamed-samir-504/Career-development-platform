
package org.sumerge.careerpackageservice.Entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.sumerge.careerpackageservice.Enums.PackageStatus;
import java.util.UUID;

import java.util.List;

@Data
@Entity
public class UserCareerPackage {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private UUID userId;
    private UUID reviewerId;

    @Enumerated(EnumType.STRING)
    private PackageStatus status;

    @ManyToOne
    @JoinColumn(name = "template_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CareerPackageTemplate template;

    @OneToMany(mappedBy = "userCareerPackage", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UserSectionResponse> sectionResponses;
}
