
package org.sumerge.careerpackageservice.Controller;

import org.sumerge.careerpackageservice.Entity.CareerPackageTemplate;
import org.sumerge.careerpackageservice.Service.CareerPackageTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/career-package-template")
public class CareerPackageTemplateController {

    @Autowired
    private CareerPackageTemplateService careerPackageTemplateService;

    @GetMapping
    public List<CareerPackageTemplate> getAll() {
        return careerPackageTemplateService.getAll();
    }

    @GetMapping("/{id}")
    public Optional<CareerPackageTemplate> getById(@PathVariable UUID id) {
        return careerPackageTemplateService.getById(id);
    }

    @PostMapping
    public CareerPackageTemplate create(@RequestBody CareerPackageTemplate obj) {
        return careerPackageTemplateService.create(obj);
    }

    @PutMapping
    public CareerPackageTemplate update(@RequestBody CareerPackageTemplate obj) {
        return careerPackageTemplateService.create(obj);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        careerPackageTemplateService.delete(id);
    }

}
