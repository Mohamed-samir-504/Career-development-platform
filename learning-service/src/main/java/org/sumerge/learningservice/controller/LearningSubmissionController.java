package org.sumerge.learningservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.sumerge.learningservice.dto.submission.LearningSubmissionDTO;
import org.sumerge.learningservice.service.LearningSubmissionService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/learning/submissions")
@RequiredArgsConstructor
public class LearningSubmissionController {

    private final LearningSubmissionService submissionService;

    @PostMapping
    public ResponseEntity<LearningSubmissionDTO> submit(@RequestBody LearningSubmissionDTO submissionDTO) {
        submissionService.sendNotificationToManager(submissionDTO);
        return ResponseEntity.ok(submissionService.saveSubmission(submissionDTO));
    }




    @GetMapping("/user/{userId}")
    public ResponseEntity<List<LearningSubmissionDTO>> getByUser(@PathVariable UUID userId) {
        return ResponseEntity.ok(submissionService.getSubmissionsByUser(userId));
    }

    @GetMapping("/template/{templateId}")
    public ResponseEntity<List<LearningSubmissionDTO>> getByTemplate(@PathVariable UUID templateId) {
        return ResponseEntity.ok(submissionService.getSubmissionsByTemplate(templateId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LearningSubmissionDTO> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(submissionService.getSubmission(id));
    }

    @PostMapping("/{id}/review")
    public ResponseEntity<Void> reviewSubmission(
            @PathVariable UUID id,
            @RequestParam boolean accepted) {
        LearningSubmissionDTO dto = submissionService.reviewSubmission(id, accepted);
        submissionService.sendNotificationToUser(dto);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        submissionService.deleteSubmission(id);
        return ResponseEntity.noContent().build();
    }
}
