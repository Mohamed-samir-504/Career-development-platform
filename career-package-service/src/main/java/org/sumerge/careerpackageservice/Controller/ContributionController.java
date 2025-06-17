
package org.sumerge.careerpackageservice.Controller;

import org.sumerge.careerpackageservice.Entity.Contribution;
import org.sumerge.careerpackageservice.Service.ContributionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/contributions")
public class ContributionController {

    @Autowired
    private ContributionService service;

    @GetMapping
    public List<Contribution> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Optional<Contribution> getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public Contribution create(@RequestBody Contribution contribution) {
        return service.save(contribution);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
