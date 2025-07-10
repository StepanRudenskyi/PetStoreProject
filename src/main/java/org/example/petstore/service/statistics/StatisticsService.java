package org.example.petstore.service.statistics;

import lombok.RequiredArgsConstructor;
import org.example.petstore.dto.stats.StatDto;
import org.example.petstore.dto.stats.SummaryStatDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class StatisticsService {
    private final List<StatisticsProvider> statisticsProviders;
    public SummaryStatDto buildStatistics() {
        List<StatDto> stats = statisticsProviders.stream()
                .map(StatisticsProvider::getStatistic)
                .toList();
        return new SummaryStatDto(stats);
    }
}
