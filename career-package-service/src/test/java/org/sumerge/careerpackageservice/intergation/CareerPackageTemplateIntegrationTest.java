package org.sumerge.careerpackageservice.intergation;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.sumerge.careerpackageservice.Controller.CareerPackageTemplateController;
import org.sumerge.careerpackageservice.Dto.Request.CreateCareerPackageRequest;
import org.sumerge.careerpackageservice.Repository.CareerPackageTemplateRepository;
import org.sumerge.careerpackageservice.Service.CareerPackageTemplateService;
import org.sumerge.careerpackageservice.config.TestSecurityConfig;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class CareerPackageTemplateIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;


    @Autowired
    private CareerPackageTemplateService careerPackageTemplateService;

    @BeforeEach
    void clearDatabase() {
        careerPackageTemplateService.getAll().forEach(packageDto -> careerPackageTemplateService.delete(packageDto.getId()));
    }

    @Test
    @Transactional
    void testCreateAndGetCareerPackageTemplate() throws Exception {

        CreateCareerPackageRequest request = new CreateCareerPackageRequest("Integration Test", "Integration Desc");

        mockMvc.perform(post("/career-package-template")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Integration Test"))
                .andReturn().getResponse().getContentAsString();



        mockMvc.perform(get("/career-package-template"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].title").value("Integration Test"));


        assertThat(careerPackageTemplateService.getAll()).hasSize(1);
    }
}

