
package org.sumerge.careerpackageservice.Controller;

import org.sumerge.careerpackageservice.Entity.PackageSectionTemplate;
import org.sumerge.careerpackageservice.Service.PackageSectionTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sections")
public class PackageSectionTemplateController {

    @Autowired
    private PackageSectionTemplateService service;

    @GetMapping
    public List<PackageSectionTemplate> getAll() {
        return service.getAll();
    }

    @PostMapping
    public PackageSectionTemplate create(@RequestBody PackageSectionTemplate section) {
        return service.save(section);
    }
}
