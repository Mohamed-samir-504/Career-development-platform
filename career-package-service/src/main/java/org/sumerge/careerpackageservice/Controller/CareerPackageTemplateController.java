
package org.sumerge.careerpackageservice.Controller;

import org.springframework.http.ResponseEntity;
import org.sumerge.careerpackageservice.Dto.CareerPackageTemplateDTO;
import org.sumerge.careerpackageservice.Dto.Request.CareerPackageEditRequest;
import org.sumerge.careerpackageservice.Dto.Request.CreateCareerPackageRequest;
import org.sumerge.careerpackageservice.Entity.CareerPackageTemplate;
import org.sumerge.careerpackageservice.Service.CareerPackageTemplateService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/career-package-template")
public class CareerPackageTemplateController {

    private final CareerPackageTemplateService careerPackageTemplateService;

    public CareerPackageTemplateController(CareerPackageTemplateService careerPackageTemplateService) {
        this.careerPackageTemplateService = careerPackageTemplateService;

    }

    @GetMapping
    public ResponseEntity<List<CareerPackageTemplateDTO>> getAll() {

        List<CareerPackageTemplateDTO> templateList = careerPackageTemplateService.getAll();
        return ResponseEntity.ok(templateList);
    }

    @GetMapping("/{id}")
    public Optional<CareerPackageTemplate> getById(@PathVariable UUID id) {
        return careerPackageTemplateService.getById(id);
    }

    @PostMapping
    public ResponseEntity<CareerPackageTemplateDTO> createNewPackage(@RequestBody CreateCareerPackageRequest request) {
        CareerPackageTemplateDTO createdPackage = careerPackageTemplateService.createNewPackage(request);
        return ResponseEntity.ok(createdPackage);
    }

    @PutMapping
    public CareerPackageTemplate update(@RequestBody CareerPackageTemplate obj) {
        return careerPackageTemplateService.create(obj);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        careerPackageTemplateService.delete(id);
    }

    @PatchMapping("/{id}/sync")
    public ResponseEntity<?> syncChanges(
            @PathVariable UUID id,
            @RequestBody CareerPackageEditRequest request) {

        careerPackageTemplateService.syncChanges(id, request);
        return ResponseEntity.ok().build();
    }

}
