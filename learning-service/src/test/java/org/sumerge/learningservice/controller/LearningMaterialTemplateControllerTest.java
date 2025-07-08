package org.sumerge.learningservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.sumerge.learningservice.config.TestSecurityConfig;
import org.sumerge.learningservice.dto.template.LearningMaterialTemplateDTO;
import org.sumerge.learningservice.service.LearningMaterialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LearningMaterialTemplateController.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = {LearningMaterialTemplateController.class, TestSecurityConfig.class})
class LearningMaterialTemplateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LearningMaterialService materialService;

    @Autowired
    private ObjectMapper objectMapper;

    private LearningMaterialTemplateDTO templateDto;

    @BeforeEach
    void setUp() {
        templateDto = new LearningMaterialTemplateDTO();
        templateDto.setId(UUID.randomUUID());
        templateDto.setTitle("Sample Template");
        templateDto.setDescription("Description");
        templateDto.setPoints(100);
    }

    @Test
    void createTemplate_shouldReturnCreatedTemplate() throws Exception {
        Mockito.when(materialService.createTemplate(any())).thenReturn(templateDto);

        mockMvc.perform(post("/templates")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(templateDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Sample Template"));
    }

    @Test
    void getAllTemplates_shouldReturnList() throws Exception {
        Mockito.when(materialService.getAllTemplates()).thenReturn(List.of(templateDto));

        mockMvc.perform(get("/templates"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Sample Template"));
    }

    @Test
    void getTemplateById_shouldReturnTemplate() throws Exception {
        UUID id = UUID.randomUUID();
        Mockito.when(materialService.getTemplateById(id)).thenReturn(templateDto);

        mockMvc.perform(get("/templates/" + id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Sample Template"));
    }

    @Test
    void getTemplateByCareerPackageId_shouldReturnList() throws Exception {
        UUID careerPackageId = UUID.randomUUID();
        Mockito.when(materialService.getTemplateByCareerPackageId(careerPackageId)).thenReturn(List.of(templateDto));

        mockMvc.perform(get("/templates/career-package/" + careerPackageId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].title").value("Sample Template"));
    }

    @Test
    void updateTemplate_shouldReturnNoContent() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(put("/templates/" + id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(templateDto)))
                .andExpect(status().isNoContent());

        Mockito.verify(materialService).update(eq(id), any(LearningMaterialTemplateDTO.class));
    }

    @Test
    void deleteTemplate_shouldReturnNoContent() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/templates/" + id))
                .andExpect(status().isOk());

        Mockito.verify(materialService).deleteById(id);
    }
}
