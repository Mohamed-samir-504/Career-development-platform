
package org.sumerge.careerpackageservice.Controller;

import org.sumerge.careerpackageservice.Entity.CareerPackageTemplate;
import org.sumerge.careerpackageservice.Service.CareerPackageTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/career-packages")
public class CareerPackageTemplateController {

    @Autowired
    private CareerPackageTemplateService service;

    @GetMapping
    public List<CareerPackageTemplate> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Optional<CareerPackageTemplate> getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public CareerPackageTemplate create(@RequestBody CareerPackageTemplate careerPackage) {
        return service.save(careerPackage);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
