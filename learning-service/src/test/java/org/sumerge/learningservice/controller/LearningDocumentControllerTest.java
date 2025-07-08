package org.sumerge.learningservice.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.sumerge.learningservice.config.TestSecurityConfig;
import org.sumerge.learningservice.entity.LearningDocument;
import org.sumerge.learningservice.enums.DocumentCategory;
import org.sumerge.learningservice.service.LearningDocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LearningDocumentController.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = {LearningDocumentController.class, TestSecurityConfig.class})

class LearningDocumentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LearningDocumentService documentService;

    private MockMultipartFile mockFile;

    @BeforeEach
    void setUp() {
        mockFile = new MockMultipartFile(
                "file",
                "test.txt",
                "text/plain",
                "Test content".getBytes()
        );
    }

    @Test
    void upload_shouldReturnFileId() throws Exception {
        Mockito.when(documentService.uploadFile(any(), anyString(), eq(DocumentCategory.SUBMISSION)))
                .thenReturn("123");

        mockMvc.perform(multipart("/files/upload")
                        .file(mockFile)
                        .param("userId", "user-1")
                        .param("category", "SUBMISSION"))
                .andExpect(status().isOk())
                .andExpect(content().string("123"));
    }

    @Test
    void uploadTemplateFile_shouldReturnFileId() throws Exception {
        Mockito.when(documentService.uploadFile(any(), anyString(), eq(DocumentCategory.TEMPLATE_ATTACHMENT)))
                .thenReturn("template-id");

        mockMvc.perform(multipart("/files/upload/template")
                        .file(mockFile)
                        .param("userId", "user-2"))
                .andExpect(status().isOk())
                .andExpect(content().string("template-id"));
    }

    @Test
    void uploadSubmissionFile_shouldReturnFileId() throws Exception {
        Mockito.when(documentService.uploadFile(any(), anyString(), eq(DocumentCategory.SUBMISSION)))
                .thenReturn("submission-id");

        mockMvc.perform(multipart("/files/upload/submission")
                        .file(mockFile)
                        .param("userId", "user-3"))
                .andExpect(status().isOk())
                .andExpect(content().string("submission-id"));
    }

    @Test
    void uploadContentFile_shouldReturnJsonResponse() throws Exception {
        Mockito.when(documentService.uploadFile(any(), anyString(), eq(DocumentCategory.BLOG_WIKI_ATTACHMENT)))
                .thenReturn("attach-id");

        mockMvc.perform(multipart("/files/upload/content")
                        .file(mockFile)
                        .param("userId", "user-4"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.attachmentId").value("attach-id"));
    }

    @Test
    void download_shouldReturnFileBytesWithHeaders() throws Exception {
        LearningDocument doc = LearningDocument.builder()
                .fileName("mydoc.pdf")
                .contentType("application/pdf")
                .content("PDF content".getBytes())
                .build();

        Mockito.when(documentService.getFile("file-123")).thenReturn(doc);

        mockMvc.perform(get("/files/file-123"))
                .andExpect(status().isOk())
                .andExpect(header().string("Content-Type", "application/pdf"))
                .andExpect(header().string("Content-Disposition", "attachment; filename=mydoc.pdf"))
                .andExpect(content().bytes("PDF content".getBytes()));
    }
}
