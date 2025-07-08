package org.sumerge.careerpackageservice.webLayer.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.sumerge.careerpackageservice.Controller.CareerPackageTemplateController;
import org.sumerge.careerpackageservice.Dto.CareerPackageTemplateDTO;
import org.sumerge.careerpackageservice.Dto.Request.CreateCareerPackageRequest;
import org.sumerge.careerpackageservice.Service.CareerPackageTemplateService;
import org.sumerge.careerpackageservice.config.TestSecurityConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@WebMvcTest(controllers = CareerPackageTemplateController.class)
@ContextConfiguration(classes = {CareerPackageTemplateController.class, TestSecurityConfig.class})
class CareerPackageTemplateControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private CareerPackageTemplateService careerPackageTemplateService;

    private UUID sampleId;
    private CareerPackageTemplateDTO sampleDto;

    @BeforeEach
    void setUp() {
        sampleId = UUID.randomUUID();
        sampleDto = new CareerPackageTemplateDTO(sampleId, "Sample Title", "Sample Description", new ArrayList<>());
    }

    @Test
    void testGetAllTemplates() throws Exception {
        List<CareerPackageTemplateDTO> templates = List.of(sampleDto);
        Mockito.when(careerPackageTemplateService.getAll()).thenReturn(templates);

        mockMvc.perform(get("/career-package-template"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].title").value("Sample Title"));
    }

    @Test
    void testCreateNewPackage() throws Exception {
        CreateCareerPackageRequest request = new CreateCareerPackageRequest("New Title", "New Description");
        Mockito.when(careerPackageTemplateService.createNewPackage(any())).thenReturn(sampleDto);

        mockMvc.perform(post("/career-package-template")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Sample Title"));
    }

    @Test
    void testDeleteTemplate() throws Exception {
        UUID id = UUID.randomUUID();

        mockMvc.perform(delete("/career-package-template/{id}", id))
                .andExpect(status().isOk());

        Mockito.verify(careerPackageTemplateService).delete(id);
    }
}

