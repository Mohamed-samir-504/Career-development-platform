
package org.sumerge.careerpackageservice.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.sumerge.careerpackageservice.Entity.UserCareerPackage;
import org.sumerge.careerpackageservice.Service.UserCareerPackageService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user-career-packages")
public class UserCareerPackageController {

    @Autowired
    private UserCareerPackageService service;

    @GetMapping
    public List<UserCareerPackage> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Optional<UserCareerPackage> getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public UserCareerPackage create(@RequestBody UserCareerPackage careerPackage) {
        return service.save(careerPackage);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
