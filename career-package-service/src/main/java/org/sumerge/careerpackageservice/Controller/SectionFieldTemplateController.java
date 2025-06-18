
package org.sumerge.careerpackageservice.Controller;

import org.sumerge.careerpackageservice.Entity.SectionFieldTemplate;
import org.sumerge.careerpackageservice.Service.SectionFieldTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/section-field-template")
public class SectionFieldTemplateController {

    @Autowired
    private SectionFieldTemplateService sectionFieldTemplateService;

    @GetMapping
    public List<SectionFieldTemplate> getAll() {
        return sectionFieldTemplateService.getAll();
    }

    @GetMapping("/{id}")
    public Optional<SectionFieldTemplate> getById(@PathVariable UUID id) {
        return sectionFieldTemplateService.getById(id);
    }

    @PostMapping
    public SectionFieldTemplate create(@RequestBody SectionFieldTemplate obj) {
        return sectionFieldTemplateService.create(obj);
    }

    @PutMapping
    public SectionFieldTemplate update(@RequestBody SectionFieldTemplate obj) {
        return sectionFieldTemplateService.create(obj);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        sectionFieldTemplateService.delete(id);
    }

}
