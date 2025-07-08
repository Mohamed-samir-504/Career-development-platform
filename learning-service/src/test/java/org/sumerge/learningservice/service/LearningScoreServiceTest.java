package org.sumerge.learningservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.sumerge.learningservice.dto.LearningScoreResponse;
import org.sumerge.learningservice.entity.LearningScore;
import org.sumerge.learningservice.repository.LearningScoreRepository;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LearningScoreServiceTest {

    @Mock
    private LearningScoreRepository scoreRepository;

    @InjectMocks
    private LearningScoreService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getLeaderboard_shouldReturnTop10MappedToResponse() {
        List<LearningScore> topScores = List.of(
                LearningScore.builder().userId(UUID.randomUUID()).points(100).build(),
                LearningScore.builder().userId(UUID.randomUUID()).points(90).build()
        );

        when(scoreRepository.findTop10ByOrderByPointsDesc()).thenReturn(topScores);

        List<LearningScoreResponse> result = service.getLeaderboard();

        assertEquals(2, result.size());
        assertEquals(100, result.get(0).getPoints());
        assertEquals(90, result.get(1).getPoints());
        verify(scoreRepository).findTop10ByOrderByPointsDesc();
    }

    @Test
    void addPoints_shouldCreateNewScoreIfNotExist() {
        UUID userId = UUID.randomUUID();

        when(scoreRepository.findByUserId(userId)).thenReturn(Optional.empty());

        ArgumentCaptor<LearningScore> scoreCaptor = ArgumentCaptor.forClass(LearningScore.class);
        LearningScore saved = LearningScore.builder().userId(userId).points(50).lastUpdated(LocalDateTime.now()).build();
        when(scoreRepository.save(any())).thenReturn(saved);

        LearningScoreResponse result = service.addPoints(userId, 50);

        assertEquals(userId, result.getUserId());
        assertEquals(50, result.getPoints());
        verify(scoreRepository).save(scoreCaptor.capture());

        LearningScore captured = scoreCaptor.getValue();
        assertEquals(50, captured.getPoints());
        assertEquals(userId, captured.getUserId());
    }

    @Test
    void addPoints_shouldUpdateExistingScore() {
        UUID userId = UUID.randomUUID();
        LearningScore existing = LearningScore.builder()
                .userId(userId)
                .points(20)
                .lastUpdated(LocalDateTime.now().minusDays(1))
                .build();

        when(scoreRepository.findByUserId(userId)).thenReturn(Optional.of(existing));

        ArgumentCaptor<LearningScore> scoreCaptor = ArgumentCaptor.forClass(LearningScore.class);
        when(scoreRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));

        LearningScoreResponse result = service.addPoints(userId, 30);

        assertEquals(userId, result.getUserId());
        assertEquals(50, result.getPoints());
        verify(scoreRepository).save(scoreCaptor.capture());
    }

    @Test
    void getLearningScore_shouldReturnExistingScore() {
        UUID userId = UUID.randomUUID();
        LearningScore score = LearningScore.builder()
                .userId(userId)
                .points(70)
                .build();

        when(scoreRepository.findByUserId(userId)).thenReturn(Optional.of(score));

        LearningScoreResponse result = service.getLearningScore(userId);

        assertEquals(userId, result.getUserId());
        assertEquals(70, result.getPoints());
    }

    @Test
    void getLearningScore_shouldReturnZeroIfNotFound() {
        UUID userId = UUID.randomUUID();
        when(scoreRepository.findByUserId(userId)).thenReturn(Optional.empty());

        LearningScoreResponse result = service.getLearningScore(userId);

        assertEquals(userId, result.getUserId());
        assertEquals(0, result.getPoints());
    }
}
