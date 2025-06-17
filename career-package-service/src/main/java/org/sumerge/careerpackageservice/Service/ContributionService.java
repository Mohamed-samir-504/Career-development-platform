
package org.sumerge.careerpackageservice.Service;

import org.sumerge.careerpackageservice.Entity.Contribution;
import org.sumerge.careerpackageservice.Repository.ContributionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContributionService {

    @Autowired
    private ContributionRepository repository;

    public List<Contribution> getAll() {
        return repository.findAll();
    }

    public Contribution save(Contribution contribution) {
        return repository.save(contribution);
    }

    public Optional<Contribution> getById(Long id) {
        return repository.findById(id);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
