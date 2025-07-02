
package org.sumerge.careerpackageservice.Controller;

import org.springframework.http.ResponseEntity;
import org.sumerge.careerpackageservice.Dto.Request.AssignCareerPackageRequest;
import org.sumerge.careerpackageservice.Dto.UserCareerPackageDTO;
import org.sumerge.careerpackageservice.Entity.UserCareerPackage;
import org.sumerge.careerpackageservice.Service.UserCareerPackageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/user-career-package")
public class UserCareerPackageController {


    private final UserCareerPackageService userCareerPackageService;

    public UserCareerPackageController(UserCareerPackageService userCareerPackageService) {
        this.userCareerPackageService = userCareerPackageService;
    }

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
        UserCareerPackageDTO userCareerPackage = userCareerPackageService.getFullyLoadedPackage(userId);
        if (userCareerPackage == null) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(userCareerPackage);
    }


    @PostMapping("/assign")
    public ResponseEntity<UserCareerPackageDTO> assignPackage(@RequestBody AssignCareerPackageRequest request) {
        return ResponseEntity.ok(userCareerPackageService.assignPackage(request));
    }

    @PutMapping
    public UserCareerPackage update(@RequestBody UserCareerPackage obj) {
        return userCareerPackageService.create(obj);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        userCareerPackageService.delete(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserCareerPackage> updateUserCareerPackage(
            @PathVariable UUID id,
            @RequestBody UserCareerPackage request
    ) {
        UserCareerPackage updatedPackage = userCareerPackageService.updateUserCareerPackage(id, request);
        return ResponseEntity.ok(updatedPackage);
    }


}