package org.example.petstore.service.statistics;

import org.example.petstore.dto.stats.StatDto;

public interface StatisticsProvider {
    StatDto getStatistic();
}
