package org.sumerge.learningservice.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.sumerge.learningservice.entity.RankConfig;
import org.sumerge.learningservice.repository.RankConfigRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class RankConfigService {


    private final RankConfigRepository repository;

    public List<RankConfig> getAllRanksInOrder() {
        return repository.findAllByOrderByPointsRequiredAsc();
    }


    public Optional<RankConfig> getRankByPoints(int points) {
        return repository.findAllByOrderByPointsRequiredAsc()
                .stream()
                .filter(rank -> points >= rank.getPointsRequired())
                .reduce((first, second) -> second);
    }

    @Transactional
    public void replaceAllRanks(List<RankConfig> newRanks) {
        validateRanks(newRanks);
        repository.deleteAllInBatch();
        repository.saveAll(newRanks);
    }

    private void validateRanks(List<RankConfig> ranks) {
        Set<String> seenNames = new HashSet<>();
        Set<Integer> seenPoints = new HashSet<>();

        for (RankConfig rank : ranks) {
            if (rank.getName() == null || rank.getName().trim().isEmpty()) {
                throw new IllegalArgumentException("Rank name must not be blank");
            }

            if (!seenNames.add(rank.getName())) {
                throw new IllegalArgumentException("Duplicate rank name: " + rank.getName());
            }

            if (rank.getPointsRequired() < 0) {
                throw new IllegalArgumentException("Points must be non-negative for rank: " + rank.getName());
            }

            if (!seenPoints.add(rank.getPointsRequired())) {
                throw new IllegalArgumentException("Duplicate points value: " + rank.getPointsRequired());
            }
        }
    }

}

