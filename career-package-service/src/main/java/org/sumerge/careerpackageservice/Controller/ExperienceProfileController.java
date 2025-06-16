
package org.sumerge.careerpackageservice.Controller;

import org.sumerge.careerpackageservice.Entity.Contribution;
import org.sumerge.careerpackageservice.Entity.ExperienceProfile;
import org.sumerge.careerpackageservice.Service.ExperienceProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/experience-profiles")
public class ExperienceProfileController {

    @Autowired
    private ExperienceProfileService service;

    @GetMapping
    public List<ExperienceProfile> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Optional<ExperienceProfile> getById(@PathVariable Long id) {
        return service.getById(id);
    }


    @PostMapping
    public ExperienceProfile create(@RequestBody ExperienceProfile profile) {
        return service.save(profile);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
