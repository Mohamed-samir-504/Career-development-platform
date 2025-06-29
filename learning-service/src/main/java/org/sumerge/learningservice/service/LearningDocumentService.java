package org.sumerge.learningservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.sumerge.learningservice.entity.LearningDocument;
import org.sumerge.learningservice.enums.DocumentCategory;
import org.sumerge.learningservice.repository.LearningDocumentRepository;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class LearningDocumentService {

    private final LearningDocumentRepository documentRepository;

    public String uploadFile(MultipartFile file, String userId, DocumentCategory category) throws IOException {
        LearningDocument doc = LearningDocument.builder()
                .fileName(file.getOriginalFilename())
                .contentType(file.getContentType())
                .content(file.getBytes())
                .userId(userId)
                .category(category)
                .build();

        return documentRepository.save(doc).getId();
    }

    public LearningDocument getFile(String id) {
        return documentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("File not found"));
    }
}