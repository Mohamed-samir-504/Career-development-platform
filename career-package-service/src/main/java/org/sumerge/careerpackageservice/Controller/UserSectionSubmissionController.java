
package org.sumerge.careerpackageservice.Controller;

import org.springframework.http.ResponseEntity;
import org.sumerge.careerpackageservice.Dto.Request.SubmitUserSectionRequest;
import org.sumerge.careerpackageservice.Entity.UserSectionSubmission;
import org.sumerge.careerpackageservice.Service.UserSectionSubmissionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/user-section-response")
public class UserSectionSubmissionController {


    private final UserSectionSubmissionService userSectionSubmissionService;

    public UserSectionSubmissionController(UserSectionSubmissionService userSectionSubmissionService) {
        this.userSectionSubmissionService = userSectionSubmissionService;
    }

    @GetMapping
    public List<UserSectionSubmission> getAll() {
        return userSectionSubmissionService.getAll();
    }

    @GetMapping("/{id}")
    public Optional<UserSectionSubmission> getById(@PathVariable UUID id) {
        return userSectionSubmissionService.getById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable UUID id) {
        userSectionSubmissionService.delete(id);
    }

    @PostMapping
    public ResponseEntity<UserSectionSubmission> submit(@RequestBody SubmitUserSectionRequest request) {
        return ResponseEntity.ok(userSectionSubmissionService.submitSection(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserSectionSubmission> update(@PathVariable UUID id, @RequestBody SubmitUserSectionRequest request) {
        return ResponseEntity.ok(userSectionSubmissionService.updateSection(id, request));
    }
}
