package org.sumerge.learningservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.sumerge.learningservice.dto.template.LearningMaterialTemplateDTO;
import org.sumerge.learningservice.service.LearningMaterialService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/templates")
@RequiredArgsConstructor
public class LearningMaterialTemplateController {

    private final LearningMaterialService materialService;

    @PostMapping
    public ResponseEntity<LearningMaterialTemplateDTO> createTemplate(@RequestBody LearningMaterialTemplateDTO dto) {
        return ResponseEntity.ok(materialService.createTemplate(dto));
    }

    @GetMapping
    public ResponseEntity<List<LearningMaterialTemplateDTO>> getAllTemplates() {
        return ResponseEntity.ok(materialService.getAllTemplates());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LearningMaterialTemplateDTO> getTemplateById(@PathVariable UUID id) {
        return ResponseEntity.ok(materialService.getTemplateById(id));
    }
}

