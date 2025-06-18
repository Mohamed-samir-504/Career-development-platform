
package org.sumerge.careerpackageservice.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.sumerge.careerpackageservice.Dto.UserCareerPackageDTO;
import org.sumerge.careerpackageservice.Entity.UserCareerPackage;
import org.sumerge.careerpackageservice.Mapper.UserCareerPackageMapper;
import org.sumerge.careerpackageservice.Service.UserCareerPackageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user-career-package")
public class UserCareerPackageController {


    private final UserCareerPackageService userCareerPackageService;

    private final UserCareerPackageMapper mapper;

    @GetMapping
    public List<UserCareerPackage> getAll() {
        return userCareerPackageService.getAll();
    }

    @GetMapping("/{id}")
    public Optional<UserCareerPackage> getById(@PathVariable UUID id) {
        return userCareerPackageService.getById(id);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<UserCareerPackageDTO> getUserCareerPackage(@PathVariable UUID userId) {
        UserCareerPackage entity = userCareerPackageService.getFullyLoadedPackageByUserId(userId);
        if (entity == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(mapper.toDto(entity));
    }


    @PostMapping
    public UserCareerPackage create(@RequestBody UserCareerPackage obj) {
        return userCareerPackageService.create(obj);
    }

    @PutMapping
    public UserCareerPackage update(@RequestBody UserCareerPackage obj) {
        return userCareerPackageService.create(obj);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        userCareerPackageService.delete(id);
    }

}
