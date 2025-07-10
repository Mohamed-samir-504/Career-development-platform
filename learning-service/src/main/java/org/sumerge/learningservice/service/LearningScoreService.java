package org.sumerge.learningservice.service;

import org.sumerge.learningservice.dto.LearningScoreResponse;
import org.sumerge.learningservice.entity.LearningScore;
import org.sumerge.learningservice.repository.LearningScoreRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class LearningScoreService {

    private final LearningScoreRepository scoreRepository;

    public LearningScoreService(LearningScoreRepository scoreRepository) {
        this.scoreRepository = scoreRepository;
    }

    public List<LearningScoreResponse> getLeaderboard() {
        return scoreRepository.findTop10ByOrderByPointsDesc()
                .stream()
                .map(score -> new LearningScoreResponse(score.getUserId(), score.getPoints()))
                .collect(Collectors.toList());
    }

    public List<LearningScoreResponse> getAllScores() {
        return scoreRepository.findAll().stream()
                .map(score -> new LearningScoreResponse(score.getUserId(), score.getPoints()))
                .collect(Collectors.toList());
    }


    public LearningScoreResponse addPoints(UUID userId, int points) {
        LearningScore score = scoreRepository.findByUserId(userId).orElse(null);

        if (score == null) {
            score = LearningScore.builder()
                    .userId(userId)
                    .points(points)
                    .lastUpdated(LocalDateTime.now())
                    .build();
        } else {
            score.setPoints(score.getPoints() + points);
            score.setLastUpdated(LocalDateTime.now());
        }

        LearningScore savedScore = scoreRepository.save(score);
        return new LearningScoreResponse(savedScore.getUserId(), savedScore.getPoints());
    }

    public LearningScoreResponse getLearningScore(UUID userId) {
        LearningScore score = scoreRepository.findByUserId(userId)
                .orElse(LearningScore.builder()
                        .userId(userId)
                        .points(0)
                        .lastUpdated(LocalDateTime.now())
                        .build());
        return new LearningScoreResponse(score.getUserId(), score.getPoints());
    }
}
