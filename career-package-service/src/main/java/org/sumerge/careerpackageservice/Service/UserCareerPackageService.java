
package org.sumerge.careerpackageservice.Service;

import org.sumerge.careerpackageservice.Entity.UserCareerPackage;
import org.sumerge.careerpackageservice.Repository.UserCareerPackageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public UserCareerPackage create(UserCareerPackage obj) {
        return repository.save(obj);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }

    public UserCareerPackage getByUserId(Long userId) {
        return repository.findByUserId(userId);
    }

}
