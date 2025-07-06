
package org.sumerge.careerpackageservice.Controller;

import org.springframework.http.ResponseEntity;
import org.sumerge.careerpackageservice.Dto.Request.SubmitUserSectionRequest;
import org.sumerge.careerpackageservice.Entity.UserSectionSubmission;
import org.sumerge.careerpackageservice.Service.UserSectionResponseService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/user-section-response")
public class UserSectionResponseController {


    private final UserSectionResponseService userSectionResponseService;

    public UserSectionResponseController(UserSectionResponseService userSectionResponseService) {
        this.userSectionResponseService = userSectionResponseService;
    }

    @GetMapping
    public List<UserSectionSubmission> getAll() {
        return userSectionResponseService.getAll();
    }

    @GetMapping("/{id}")
    public Optional<UserSectionSubmission> getById(@PathVariable UUID id) {
        return userSectionResponseService.getById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        userSectionResponseService.delete(id);
    }

    @PostMapping
    public ResponseEntity<UserSectionSubmission> submit(@RequestBody SubmitUserSectionRequest request) {
        return ResponseEntity.ok(userSectionResponseService.submitSection(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserSectionSubmission> update(@PathVariable UUID id, @RequestBody SubmitUserSectionRequest request) {
        return ResponseEntity.ok(userSectionResponseService.updateSection(id, request));
    }
}
