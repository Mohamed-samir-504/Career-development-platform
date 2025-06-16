
package org.sumerge.careerpackageservice.Controller;

import org.sumerge.careerpackageservice.Entity.PackageSection;
import org.sumerge.careerpackageservice.Service.PackageSectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sections")
public class PackageSectionController {

    @Autowired
    private PackageSectionService service;

    @GetMapping
    public List<PackageSection> getAll() {
        return service.getAll();
    }

    @PostMapping
    public PackageSection create(@RequestBody PackageSection section) {
        return service.save(section);
    }
}
