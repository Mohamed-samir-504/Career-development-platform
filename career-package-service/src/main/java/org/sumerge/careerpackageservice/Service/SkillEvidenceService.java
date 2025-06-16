
package org.sumerge.careerpackageservice.Service;

import org.sumerge.careerpackageservice.Entity.Learning;
import org.sumerge.careerpackageservice.Entity.SkillEvidence;
import org.sumerge.careerpackageservice.Repository.SkillEvidenceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SkillEvidenceService {

    @Autowired
    private SkillEvidenceRepository repository;

    public List<SkillEvidence> getAll() {
        return repository.findAll();
    }

    public SkillEvidence save(SkillEvidence skill) {
        return repository.save(skill);
    }

    public Optional<SkillEvidence> getById(Long id) {
        return repository.findById(id);
    }

    public void delete(Long id) {
        repository.deleteById(id);
    }
}
