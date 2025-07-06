
package org.sumerge.careerpackageservice.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.sumerge.careerpackageservice.Enums.PackageStatus;
import java.util.UUID;

import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class UserCareerPackage {

    public UserCareerPackage(UUID userId,UUID reviewerId,PackageStatus status,CareerPackageTemplate template) {

        this.userId = userId;
        this.reviewerId = reviewerId;
        this.status = status;
        this.template = template;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private UUID userId;
    private UUID reviewerId;

    @Enumerated(EnumType.STRING)
    private PackageStatus status;
    private String reviewerComment;

    @ManyToOne
    @JoinColumn(name = "template_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private CareerPackageTemplate template;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_career_package_id")
    private List<UserSectionSubmission> sectionResponses;
}
