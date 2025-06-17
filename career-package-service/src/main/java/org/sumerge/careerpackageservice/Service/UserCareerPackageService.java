
package org.sumerge.careerpackageservice.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sumerge.careerpackageservice.Entity.UserCareerPackage;
import org.sumerge.careerpackageservice.Repository.UserCareerPackageRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserCareerPackageService {

    @Autowired
    private UserCareerPackageRepository repository;

    public List<UserCareerPackage> getAll() {
        return repository.findAll();
    }

    public Optional<UserCareerPackage> getById(Long id) {
        return repository.findById(id);
    }

    public UserCareerPackage save(UserCareerPackage careerPackage) {
        return repository.save(careerPackage);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
