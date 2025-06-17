
package org.sumerge.careerpackageservice.Service;

import org.sumerge.careerpackageservice.Entity.Learning;
import org.sumerge.careerpackageservice.Repository.LearningRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LearningService {

    @Autowired
    private LearningRepository repository;

    public List<Learning> getAll() {
        return repository.findAll();
    }

    public Learning save(Learning learning) {
        return repository.save(learning);
    }

    public Optional<Learning> getById(Long id) {
        return repository.findById(id);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
