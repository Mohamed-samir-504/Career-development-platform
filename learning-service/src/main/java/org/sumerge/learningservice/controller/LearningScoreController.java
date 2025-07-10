package org.sumerge.learningservice.controller;

import org.sumerge.learningservice.dto.LearningScoreResponse;
import org.sumerge.learningservice.service.LearningScoreService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/scores")
@CrossOrigin
public class LearningScoreController {

    private final LearningScoreService scoreService;

    public LearningScoreController(LearningScoreService scoreService) {
        this.scoreService = scoreService;
    }

    @GetMapping("/leaderboard")
    public List<LearningScoreResponse> getLeaderboard() {
        return scoreService.getLeaderboard();
    }

    @GetMapping("")
    public List<LearningScoreResponse> getAll() {
        return scoreService.getAllScores();
    }


    @PostMapping("/add")
    public LearningScoreResponse addPoints(@RequestParam UUID userId, @RequestParam int points) {
        return scoreService.addPoints(userId, points);
    }

    @GetMapping("/user/{userId}")
    public LearningScoreResponse getLearningScore(@PathVariable UUID userId) {
        return scoreService.getLearningScore(userId);
    }
}
