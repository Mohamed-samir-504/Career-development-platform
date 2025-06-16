
package org.sumerge.careerpackageservice.Controller;

import org.sumerge.careerpackageservice.Entity.Contribution;
import org.sumerge.careerpackageservice.Entity.SkillEvidence;
import org.sumerge.careerpackageservice.Service.SkillEvidenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/skill-evidences")
public class SkillEvidenceController {

    @Autowired
    private SkillEvidenceService service;

    @GetMapping
    public List<SkillEvidence> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Optional<SkillEvidence> getById(@PathVariable Long id) {
        return service.getById(id);
    }


    @PostMapping
    public SkillEvidence create(@RequestBody SkillEvidence skill) {
        return service.save(skill);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
