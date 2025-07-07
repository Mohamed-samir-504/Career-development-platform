package org.sumerge.learningservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.sumerge.learningservice.entity.RankConfig;
import org.sumerge.learningservice.service.RankConfigService;

import java.util.List;

@RestController
@RequestMapping("/ranks")
@RequiredArgsConstructor
public class RankConfigController {

    private final RankConfigService rankConfigService;

    @GetMapping
    public List<RankConfig> getAllRanks() {
        return rankConfigService.getAllRanksInOrder();
    }

    @GetMapping("/by-points/{points}")
    public ResponseEntity<RankConfig> getRankByPoints(@PathVariable int points) {
        return rankConfigService.getRankByPoints(points)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping
    public ResponseEntity<Void> replaceAllRanks(@RequestBody List<RankConfig> ranks) {
        rankConfigService.replaceAllRanks(ranks);
        return ResponseEntity.ok().build();
    }


}
