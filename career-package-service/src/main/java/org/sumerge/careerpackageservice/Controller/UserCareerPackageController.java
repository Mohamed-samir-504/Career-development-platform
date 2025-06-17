
package org.sumerge.careerpackageservice.Controller;

import org.sumerge.careerpackageservice.Entity.UserCareerPackage;
import org.sumerge.careerpackageservice.Service.UserCareerPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user-career-package")
public class UserCareerPackageController {

    @Autowired
    private UserCareerPackageService userCareerPackageService;

    @GetMapping
    public List<UserCareerPackage> getAll() {
        return userCareerPackageService.getAll();
    }

    @GetMapping("/{id}")
    public Optional<UserCareerPackage> getById(@PathVariable Long id) {
        return userCareerPackageService.getById(id);
    }

    @PostMapping
    public UserCareerPackage create(@RequestBody UserCareerPackage obj) {
        return userCareerPackageService.create(obj);
    }

    @PutMapping
    public UserCareerPackage update(@RequestBody UserCareerPackage obj) {
        return userCareerPackageService.create(obj);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userCareerPackageService.delete(id);
    }

}
