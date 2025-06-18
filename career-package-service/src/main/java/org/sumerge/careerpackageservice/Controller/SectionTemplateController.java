
package org.sumerge.careerpackageservice.Controller;

import org.sumerge.careerpackageservice.Entity.SectionTemplate;
import org.sumerge.careerpackageservice.Service.SectionTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/section-template")
public class SectionTemplateController {

    @Autowired
    private SectionTemplateService sectionTemplateService;

    @GetMapping
    public List<SectionTemplate> getAll() {
        return sectionTemplateService.getAll();
    }

    @GetMapping("/{id}")
    public Optional<SectionTemplate> getById(@PathVariable UUID id) {
        return sectionTemplateService.getById(id);
    }

    @PostMapping
    public SectionTemplate create(@RequestBody SectionTemplate obj) {
        return sectionTemplateService.create(obj);
    }

    @PutMapping
    public SectionTemplate update(@RequestBody SectionTemplate obj) {
        return sectionTemplateService.create(obj);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        sectionTemplateService.delete(id);
    }

}
