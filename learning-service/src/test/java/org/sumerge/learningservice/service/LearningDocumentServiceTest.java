package org.sumerge.learningservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.mock.web.MockMultipartFile;
import org.sumerge.learningservice.entity.LearningDocument;
import org.sumerge.learningservice.enums.DocumentCategory;
import org.sumerge.learningservice.repository.LearningDocumentRepository;

import java.io.IOException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LearningDocumentServiceTest {

    @Mock
    private LearningDocumentRepository documentRepository;

    @InjectMocks
    private LearningDocumentService learningDocumentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void uploadFile_shouldSaveDocumentAndReturnId() throws IOException {
        // Arrange
        byte[] content = "test content".getBytes();
        MockMultipartFile file = new MockMultipartFile(
                "file", "test.txt", "text/plain", content
        );

        LearningDocument savedDoc = LearningDocument.builder()
                .id("123")
                .fileName("test.txt")
                .contentType("text/plain")
                .content(content)
                .userId("user-1")
                .category(DocumentCategory.SUBMISSION)
                .build();

        when(documentRepository.save(any(LearningDocument.class))).thenReturn(savedDoc);

        // Act
        String id = learningDocumentService.uploadFile(file, "user-1", DocumentCategory.BLOG_WIKI_ATTACHMENT);

        // Assert
        assertEquals("123", id);
        verify(documentRepository).save(any(LearningDocument.class));
    }

    @Test
    void getFile_shouldReturnDocumentIfExists() {
        LearningDocument doc = LearningDocument.builder()
                .id("123")
                .fileName("test.txt")
                .build();

        when(documentRepository.findById("123")).thenReturn(Optional.of(doc));

        LearningDocument result = learningDocumentService.getFile("123");

        assertEquals("test.txt", result.getFileName());
        verify(documentRepository).findById("123");
    }

    @Test
    void getFile_shouldThrowIfNotFound() {
        when(documentRepository.findById("404")).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class,
                () -> learningDocumentService.getFile("404"));

        assertEquals("File not found", ex.getMessage());
        verify(documentRepository).findById("404");
    }
}
