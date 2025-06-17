
package org.sumerge.careerpackageservice.Service;

import org.sumerge.careerpackageservice.Entity.ExperienceProfile;
import org.sumerge.careerpackageservice.Repository.ExperienceProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ExperienceProfileService {

    @Autowired
    private ExperienceProfileRepository repository;

    public List<ExperienceProfile> getAll() {
        return repository.findAll();
    }

    public ExperienceProfile save(ExperienceProfile profile) {
        return repository.save(profile);
    }

    public Optional<ExperienceProfile> getById(Long id) {
        return repository.findById(id);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
