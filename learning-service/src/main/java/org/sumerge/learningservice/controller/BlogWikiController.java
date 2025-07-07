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
@RequestMapping("/content")
@RequiredArgsConstructor
public class BlogWikiController {

    private final BlogWikiService service;

    @PostMapping("/blogs")
    public ResponseEntity<BlogWiki> createBlog(@RequestBody BlogWikiDTO request) {
        return ResponseEntity.ok(service.createBlog(request.getTitle(), request.getContent(), request.getAttachmentId()));
    }

    @PostMapping("/wikis")
    public ResponseEntity<BlogWiki> createWiki(@RequestBody BlogWikiDTO request) {
        return ResponseEntity.ok(service.createWiki(request.getTitle(), request.getContent(), request.getAttachmentId()));
    }

    @GetMapping("/blogs")
    public ResponseEntity<List<BlogWiki>> getAllBlogs() {
        return ResponseEntity.ok(service.getByType(ContentType.BLOG));
    }

    @GetMapping("/wikis")
    public ResponseEntity<List<BlogWiki>> getAllWikis() {
        return ResponseEntity.ok(service.getByType(ContentType.WIKI));
    }

    @GetMapping("/entry/{id}")
    public ResponseEntity<BlogWiki> getEntryById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getById(id));
    }



    @DeleteMapping("/entry/{id}")
    public ResponseEntity<Void> deleteEntry(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }


    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable UUID id, @RequestBody BlogWikiDTO updatedDto) {
        service.update(id, updatedDto);
        return ResponseEntity.noContent().build();
    }
}
