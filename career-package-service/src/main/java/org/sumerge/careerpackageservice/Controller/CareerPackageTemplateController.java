
package org.sumerge.careerpackageservice.Controller;

import org.springframework.http.ResponseEntity;
import org.sumerge.careerpackageservice.Dto.CareerPackageTemplateDTO;
import org.sumerge.careerpackageservice.Dto.Request.CareerPackageEditRequest;
import org.sumerge.careerpackageservice.Entity.CareerPackageTemplate;
import org.sumerge.careerpackageservice.Mapper.UserCareerPackageMapper;
import org.sumerge.careerpackageservice.Service.CareerPackageTemplateService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/career-package-template")
public class CareerPackageTemplateController {


    private final CareerPackageTemplateService careerPackageTemplateService;
    private final UserCareerPackageMapper mapper;

    public CareerPackageTemplateController(CareerPackageTemplateService careerPackageTemplateService, UserCareerPackageMapper mapper) {
        this.careerPackageTemplateService = careerPackageTemplateService;
        this.mapper = mapper;
    }

    @GetMapping
    public ResponseEntity<List<CareerPackageTemplateDTO>> getAll() {

        List<CareerPackageTemplate> entityList = careerPackageTemplateService.getAll();
        return ResponseEntity.ok(mapper.toCareerPackageDtoList(entityList));
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

    @PatchMapping("/{id}/sync")
    public ResponseEntity<?> syncChanges(
            @PathVariable UUID id,
            @RequestBody CareerPackageEditRequest request) {

        careerPackageTemplateService.syncChanges(id, request);
        return ResponseEntity.ok().build();
    }

}
