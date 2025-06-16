
package org.sumerge.careerpackageservice.Controller;

import org.sumerge.careerpackageservice.Entity.CareerPackage;
import org.sumerge.careerpackageservice.Service.CareerPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/career-packages")
public class CareerPackageController {

    @Autowired
    private CareerPackageService service;

    @GetMapping
    public List<CareerPackage> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public Optional<CareerPackage> getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @PostMapping
    public CareerPackage create(@RequestBody CareerPackage careerPackage) {
        return service.save(careerPackage);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.delete(id);
    }
}
