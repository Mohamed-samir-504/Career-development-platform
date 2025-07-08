package org.sumerge.learningservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.sumerge.learningservice.config.TestSecurityConfig;
import org.sumerge.learningservice.dto.LearningScoreResponse;
import org.sumerge.learningservice.service.LearningScoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LearningScoreController.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = {LearningScoreController.class, TestSecurityConfig.class})
class LearningScoreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private LearningScoreService scoreService;

    private UUID userId;
    private LearningScoreResponse scoreResponse;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        scoreResponse = new LearningScoreResponse(userId, 150);
    }

    @Test
    void getLeaderboard_shouldReturnScoreList() throws Exception {
        Mockito.when(scoreService.getLeaderboard()).thenReturn(List.of(scoreResponse));

        mockMvc.perform(get("/scores/leaderboard"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId").value(userId.toString()))
                .andExpect(jsonPath("$[0].points").value(150));
    }

    @Test
    void addPoints_shouldReturnUpdatedScore() throws Exception {
        Mockito.when(scoreService.addPoints(eq(userId), eq(50))).thenReturn(scoreResponse);

        mockMvc.perform(post("/scores/add")
                        .param("userId", userId.toString())
                        .param("points", "50"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(userId.toString()))
                .andExpect(jsonPath("$.points").value(150));
    }

    @Test
    void getLearningScore_shouldReturnUserScore() throws Exception {
        Mockito.when(scoreService.getLearningScore(userId)).thenReturn(scoreResponse);

        mockMvc.perform(get("/scores/user/" + userId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(userId.toString()))
                .andExpect(jsonPath("$.points").value(150));
    }
}
