
package org.sumerge.careerpackageservice.Controller;

import org.sumerge.careerpackageservice.Entity.Contribution;
import org.sumerge.careerpackageservice.Entity.Learning;
import org.sumerge.careerpackageservice.Service.LearningService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/learnings")
public class LearningController {

    @Autowired
    private LearningService service;

    @GetMapping
    public List<Learning> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Optional<Learning> getById(@PathVariable Long id) {
        return service.getById(id);
    }


    @PostMapping
    public Learning create(@RequestBody Learning learning) {
        return service.save(learning);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
