package org.sumerge.learningservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.sumerge.learningservice.entity.LearningDocument;
import org.sumerge.learningservice.enums.DocumentCategory;
import org.sumerge.learningservice.service.LearningDocumentService;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/api/learning/files")
@RequiredArgsConstructor
public class LearningDocumentController {

    private final LearningDocumentService documentService;

    @PostMapping("/upload")
    public ResponseEntity<String> upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam String userId,
            @RequestParam DocumentCategory category) throws IOException {

        String id = documentService.uploadFile(file, userId, category);
        return ResponseEntity.ok(id);
    }

    @PostMapping("/upload/template")
    public ResponseEntity<String> uploadTemplateFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam String userId) throws IOException {

        String id = documentService.uploadFile(file, userId, DocumentCategory.TEMPLATE_ATTACHMENT);
        return ResponseEntity.ok(id);
    }

    @PostMapping("/upload/submission")
    public ResponseEntity<String> uploadSubmissionFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam String userId) throws IOException {

        String id = documentService.uploadFile(file, userId, DocumentCategory.SUBMISSION);
        return ResponseEntity.ok(id);
    }

    @PostMapping("/upload/content")
    public ResponseEntity<Map<String, String>> uploadBlogWikiFile(
            @RequestParam("file") MultipartFile file,
            @RequestParam String userId) throws IOException {

        String id = documentService.uploadFile(file, userId, DocumentCategory.BLOG_WIKI_ATTACHMENT);
        Map<String, String> response = Map.of("attachmentId", id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> download(@PathVariable String id) {
        LearningDocument doc = documentService.getFile(id);
        return ResponseEntity.ok()
                .header("Content-Type", doc.getContentType())
                .header("Content-Disposition", "attachment; filename=" + doc.getFileName())
                .body(doc.getContent());
    }
}