
package org.sumerge.careerpackageservice.Service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.transaction.annotation.Transactional;
import org.sumerge.careerpackageservice.Dto.Request.AssignCareerPackageRequest;
import org.sumerge.careerpackageservice.Dto.UserCareerPackageDTO;
import org.sumerge.careerpackageservice.Entity.CareerPackageTemplate;
import org.sumerge.careerpackageservice.Entity.UserCareerPackage;
import org.sumerge.careerpackageservice.Enums.PackageStatus;
import org.sumerge.careerpackageservice.Mapper.UserCareerPackageMapper;
import org.sumerge.careerpackageservice.Repository.CareerPackageTemplateRepository;
import org.sumerge.careerpackageservice.Repository.UserCareerPackageRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserCareerPackageService {


    private final UserCareerPackageRepository userCareerPackageRepository;
    private final CareerPackageTemplateRepository templateRepository;
    private final UserCareerPackageMapper mapper;

    public UserCareerPackageService(UserCareerPackageRepository userCareerPackageRepository, CareerPackageTemplateRepository templateRepository, UserCareerPackageMapper mapper) {
        this.userCareerPackageRepository = userCareerPackageRepository;
        this.templateRepository = templateRepository;
        this.mapper = mapper;
    }

    public List<UserCareerPackage> getAll() {
        return userCareerPackageRepository.findAll();
    }

    public Optional<UserCareerPackage> getById(UUID id) {
        return userCareerPackageRepository.findById(id);
    }

    public UserCareerPackage create(UserCareerPackage obj) {
        return userCareerPackageRepository.save(obj);
    }

    public void delete(UUID id) {
        userCareerPackageRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public UserCareerPackage getFullyLoadedPackageByUserId(UUID userId) {
        UserCareerPackage pkg = userCareerPackageRepository.findByUserId(userId);

        if (pkg == null) return null;

        // Force fetch template and all sections + fields
        pkg.getTemplate().getSections().forEach(section -> {
            section.getFields().size(); // triggers fetch
        });

        // Force fetch user responses and nested field responses
        pkg.getSectionResponses().forEach(response -> {
            response.getFieldResponses().size(); // triggers fetch
        });

        return pkg;
    }

    public UserCareerPackage updateStatus(UUID id, PackageStatus status) {
        UserCareerPackage pkg = userCareerPackageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Career package not found"));

        pkg.setStatus(PackageStatus.valueOf(String.valueOf(status)));
        return userCareerPackageRepository.save(pkg);
    }

    public UserCareerPackageDTO assignPackage(AssignCareerPackageRequest request) {
        CareerPackageTemplate template = templateRepository.findById(request.getTemplateId())
                .orElseThrow(() -> new EntityNotFoundException("Template not found"));

        UserCareerPackage pkg = new UserCareerPackage();
        pkg.setUserId(request.getUserId());
        pkg.setReviewerId(request.getReviewerId());
        pkg.setStatus(request.getStatus());
        pkg.setTemplate(template);

        userCareerPackageRepository.save(pkg);
        return mapper.toDto(pkg);
    }


}