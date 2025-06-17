
package org.sumerge.careerpackageservice.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.sumerge.careerpackageservice.Entity.UserPackageSection;
import org.sumerge.careerpackageservice.Repository.UserPackageSectionRepository;

import java.util.List;

@Service
public class UserPackageSectionService {

    @Autowired
    private UserPackageSectionRepository repository;

    public List<UserPackageSection> getAll() {
        return repository.findAll();
    }

    public UserPackageSection save(UserPackageSection section) {
        return repository.save(section);
    }
}
