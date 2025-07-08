package org.sumerge.learningservice.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.sumerge.learningservice.entity.RankConfig;
import org.sumerge.learningservice.repository.RankConfigRepository;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class RankConfigServiceTest {

    @Mock
    private RankConfigRepository repository;

    @InjectMocks
    private RankConfigService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllRanksInOrder_shouldReturnList() {
        List<RankConfig> ranks = List.of(new RankConfig(), new RankConfig());
        when(repository.findAllByOrderByPointsRequiredAsc()).thenReturn(ranks);

        List<RankConfig> result = service.getAllRanksInOrder();

        assertEquals(2, result.size());
        verify(repository).findAllByOrderByPointsRequiredAsc();
    }

    @Test
    void getRankByPoints_shouldReturnCorrectRank() {
        RankConfig rank1 = new RankConfig();
        rank1.setName("Bronze");
        rank1.setPointsRequired(10);

        RankConfig rank2 = new RankConfig();
        rank2.setName("Silver");
        rank2.setPointsRequired(20);

        when(repository.findAllByOrderByPointsRequiredAsc()).thenReturn(List.of(rank1, rank2));

        Optional<RankConfig> result = service.getRankByPoints(25);

        assertTrue(result.isPresent());
        assertEquals("Silver", result.get().getName());
    }

    @Test
    void getRankByPoints_shouldReturnEmptyIfNoMatch() {
        when(repository.findAllByOrderByPointsRequiredAsc()).thenReturn(List.of());

        Optional<RankConfig> result = service.getRankByPoints(5);

        assertFalse(result.isPresent());
    }

    @Test
    void replaceAllRanks_shouldDeleteAndSaveValidRanks() {
        RankConfig rank1 = new RankConfig();
        rank1.setName("Bronze");
        rank1.setPointsRequired(10);

        RankConfig rank2 = new RankConfig();
        rank2.setName("Silver");
        rank2.setPointsRequired(20);

        List<RankConfig> newRanks = List.of(rank1, rank2);

        service.replaceAllRanks(newRanks);

        verify(repository).deleteAllInBatch();
        verify(repository).saveAll(newRanks);
    }

    @Test
    void replaceAllRanks_shouldThrowForBlankName() {
        RankConfig invalidRank = new RankConfig();
        invalidRank.setName("   "); // blank
        invalidRank.setPointsRequired(0);

        List<RankConfig> ranks = List.of(invalidRank);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.replaceAllRanks(ranks));

        assertEquals("Rank name must not be blank", ex.getMessage());
        verify(repository, never()).deleteAllInBatch();
    }

    @Test
    void replaceAllRanks_shouldThrowForDuplicateNames() {
        RankConfig rank1 = new RankConfig();
        rank1.setName("Bronze");
        rank1.setPointsRequired(10);

        RankConfig rank2 = new RankConfig();
        rank2.setName("Bronze");
        rank2.setPointsRequired(20);

        List<RankConfig> ranks = List.of(rank1, rank2);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.replaceAllRanks(ranks));

        assertTrue(ex.getMessage().contains("Duplicate rank name"));
    }

    @Test
    void replaceAllRanks_shouldThrowForNegativePoints() {
        RankConfig rank = new RankConfig();
        rank.setName("Bronze");
        rank.setPointsRequired(-1);

        List<RankConfig> ranks = List.of(rank);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.replaceAllRanks(ranks));

        assertTrue(ex.getMessage().contains("Points must be non-negative"));
    }

    @Test
    void replaceAllRanks_shouldThrowForDuplicatePoints() {
        RankConfig rank1 = new RankConfig();
        rank1.setName("Bronze");
        rank1.setPointsRequired(10);

        RankConfig rank2 = new RankConfig();
        rank2.setName("Silver");
        rank2.setPointsRequired(10);

        List<RankConfig> ranks = List.of(rank1, rank2);

        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> service.replaceAllRanks(ranks));

        assertTrue(ex.getMessage().contains("Duplicate points value"));
    }
}
