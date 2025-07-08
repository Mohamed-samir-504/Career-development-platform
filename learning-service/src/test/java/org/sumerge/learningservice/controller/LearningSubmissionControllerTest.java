package org.sumerge.learningservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.sumerge.learningservice.config.TestSecurityConfig;
import org.sumerge.learningservice.dto.submission.LearningSubmissionDTO;
import org.sumerge.learningservice.service.LearningSubmissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LearningSubmissionController.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = {LearningSubmissionController.class, TestSecurityConfig.class})
class LearningSubmissionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LearningSubmissionService submissionService;

    @Autowired
    private ObjectMapper objectMapper;

    private UUID submissionId;
    private UUID userId;
    private UUID templateId;
    private UUID managerId;
    private LearningSubmissionDTO dto;

    @BeforeEach
    void setUp() {
        submissionId = UUID.randomUUID();
        userId = UUID.randomUUID();
        templateId = UUID.randomUUID();
        managerId = UUID.randomUUID();

        dto = new LearningSubmissionDTO();
        dto.setId(submissionId);
        dto.setUserId(userId);
        dto.setTemplateId(templateId);
        dto.setManagerId(managerId);
    }

    @Test
    void submit_shouldCallNotificationAndSaveSubmission() throws Exception {
        Mockito.doNothing().when(submissionService).sendNotificationToManager(any());
        Mockito.when(submissionService.saveSubmission(any())).thenReturn(dto);

        mockMvc.perform(post("/submissions")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(submissionId.toString()));
    }

    @Test
    void getByUser_shouldReturnList() throws Exception {
        Mockito.when(submissionService.getSubmissionsByUser(userId)).thenReturn(List.of(dto));

        mockMvc.perform(get("/submissions/user/" + userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(submissionId.toString()));
    }

    @Test
    void getByTemplate_shouldReturnList() throws Exception {
        Mockito.when(submissionService.getSubmissionsByTemplate(templateId)).thenReturn(List.of(dto));

        mockMvc.perform(get("/submissions/template/" + templateId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(submissionId.toString()));
    }

    @Test
    void getByManager_shouldReturnList() throws Exception {
        Mockito.when(submissionService.getSubmissionsByManagerId(managerId)).thenReturn(List.of(dto));

        mockMvc.perform(get("/submissions/manager/" + managerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(submissionId.toString()));
    }

    @Test
    void getById_shouldReturnSubmission() throws Exception {
        Mockito.when(submissionService.getSubmission(submissionId)).thenReturn(dto);

        mockMvc.perform(get("/submissions/" + submissionId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(submissionId.toString()));
    }

    @Test
    void reviewSubmission_shouldUpdateAndNotify() throws Exception {
        Mockito.when(submissionService.reviewSubmission(eq(submissionId), eq(true))).thenReturn(dto);
        Mockito.doNothing().when(submissionService).sendNotificationToUser(dto);

        mockMvc.perform(put("/submissions/" + submissionId + "/review")
                        .param("accepted", "true"))
                .andExpect(status().isOk());

        Mockito.verify(submissionService).reviewSubmission(submissionId, true);
        Mockito.verify(submissionService).sendNotificationToUser(dto);
    }

    @Test
    void delete_shouldReturnNoContent() throws Exception {
        mockMvc.perform(delete("/submissions/" + submissionId))
                .andExpect(status().isNoContent());

        Mockito.verify(submissionService).deleteSubmission(submissionId);
    }

    @Test
    void getByUserAndTemplate_shouldReturnSubmission() throws Exception {
        Mockito.when(submissionService.getByUserAndTemplate(userId, templateId)).thenReturn(dto);

        mockMvc.perform(get("/submissions/user/" + userId + "/template/" + templateId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(submissionId.toString()));
    }
}
