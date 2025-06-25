
package org.sumerge.careerpackageservice.Service;

import org.springframework.transaction.annotation.Transactional;
import org.sumerge.careerpackageservice.Entity.UserCareerPackage;
import org.sumerge.careerpackageservice.Enums.PackageStatus;
import org.sumerge.careerpackageservice.Repository.UserCareerPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserCareerPackageService {

    @Autowired
    private UserCareerPackageRepository repository;

    public List<UserCareerPackage> getAll() {
        return repository.findAll();
    }

    public Optional<UserCareerPackage> getById(UUID id) {
        return repository.findById(id);
    }

    public UserCareerPackage create(UserCareerPackage obj) {
        return repository.save(obj);
    }

    public void delete(UUID id) {
        repository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public UserCareerPackage getFullyLoadedPackageByUserId(UUID userId) {
        UserCareerPackage pkg = repository.findByUserId(userId);

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
        UserCareerPackage pkg = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Career package not found"));

        pkg.setStatus(PackageStatus.valueOf(String.valueOf(status)));
        return repository.save(pkg);
    }


}