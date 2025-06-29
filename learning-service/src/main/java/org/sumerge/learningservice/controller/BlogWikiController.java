package org.sumerge.learningservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.sumerge.learningservice.dto.BlogWikiDTO;
import org.sumerge.learningservice.entity.BlogWiki;
import org.sumerge.learningservice.enums.ContentType;
import org.sumerge.learningservice.service.BlogWikiService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/learning/content")
@RequiredArgsConstructor
public class BlogWikiController {

    private final BlogWikiService service;

    // Create a blog
    @PostMapping("/blogs")
    public ResponseEntity<BlogWiki> createBlog(@RequestBody BlogWikiDTO request) {
        return ResponseEntity.ok(service.createBlog(request.getTitle(), request.getContent(), request.getAttachmentId()));
    }
    // Create a wiki
    @PostMapping("/wikis")
    public ResponseEntity<BlogWiki> createWiki(@RequestBody BlogWikiDTO request) {
        return ResponseEntity.ok(service.createWiki(request.getTitle(), request.getContent(), request.getAttachmentId()));
    }

    // Get all blogs
    @GetMapping("/blogs")
    public ResponseEntity<List<BlogWiki>> getAllBlogs() {
        return ResponseEntity.ok(service.getByType(ContentType.BLOG));
    }

    // Get all wikis
    @GetMapping("/wikis")
    public ResponseEntity<List<BlogWiki>> getAllWikis() {
        return ResponseEntity.ok(service.getByType(ContentType.WIKI));
    }

    // Get single entry by ID
    @GetMapping("/entry/{id}")
    public ResponseEntity<BlogWiki> getEntryById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getById(id));
    }

    // Delete by ID
    @DeleteMapping("/entry/{id}")
    public ResponseEntity<Void> deleteEntry(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
