package org.sumerge.learningservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.sumerge.learningservice.config.TestSecurityConfig;
import org.sumerge.learningservice.entity.RankConfig;
import org.sumerge.learningservice.service.RankConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RankConfigController.class)
@ActiveProfiles("test")
@ContextConfiguration(classes = {RankConfigController.class, TestSecurityConfig.class})
class RankConfigControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private RankConfigService rankConfigService;

    @Autowired
    private ObjectMapper objectMapper;

    private RankConfig rank1;
    private RankConfig rank2;

    @BeforeEach
    void setUp() {
        rank1 = new RankConfig();
        rank1.setName("Bronze");
        rank1.setPointsRequired(0);

        rank2 = new RankConfig();
        rank2.setName("Silver");
        rank2.setPointsRequired(100);
    }

    @Test
    void getAllRanks_shouldReturnOrderedList() throws Exception {
        Mockito.when(rankConfigService.getAllRanksInOrder()).thenReturn(List.of(rank1, rank2));

        mockMvc.perform(get("/ranks"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Bronze"))
                .andExpect(jsonPath("$[1].name").value("Silver"));
    }

    @Test
    void getRankByPoints_shouldReturnMatchingRank() throws Exception {
        Mockito.when(rankConfigService.getRankByPoints(150)).thenReturn(Optional.of(rank2));

        mockMvc.perform(get("/ranks/by-points/150"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Silver"));
    }

    @Test
    void getRankByPoints_shouldReturnNotFound() throws Exception {
        Mockito.when(rankConfigService.getRankByPoints(10)).thenReturn(Optional.empty());

        mockMvc.perform(get("/ranks/by-points/10"))
                .andExpect(status().isNotFound());
    }

    @Test
    void replaceAllRanks_shouldReturnOk() throws Exception {
        List<RankConfig> newRanks = List.of(rank1, rank2);

        mockMvc.perform(put("/ranks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newRanks)))
                .andExpect(status().isOk());

        Mockito.verify(rankConfigService).replaceAllRanks(Mockito.argThat(actual ->
                actual.size() == 2 &&
                        actual.get(0).getName().equals("Bronze") &&
                        actual.get(0).getPointsRequired() == 0 &&
                        actual.get(1).getName().equals("Silver") &&
                        actual.get(1).getPointsRequired() == 100
        ));
    }
}
