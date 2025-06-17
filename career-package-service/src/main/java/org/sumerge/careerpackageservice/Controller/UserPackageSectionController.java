
package org.sumerge.careerpackageservice.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.sumerge.careerpackageservice.Entity.UserPackageSection;
import org.sumerge.careerpackageservice.Service.UserPackageSectionService;

import java.util.List;

@RestController
@RequestMapping("/api/user-sections")
public class UserPackageSectionController {

    @Autowired
    private UserPackageSectionService service;

    @GetMapping
    public List<UserPackageSection> getAll() {
        return service.getAll();
    }

    @PostMapping
    public UserPackageSection create(@RequestBody UserPackageSection section) {
        return service.save(section);
    }
}
