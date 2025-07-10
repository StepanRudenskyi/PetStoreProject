package org.example.petstore.service.statistics;

import lombok.RequiredArgsConstructor;
import org.example.petstore.dto.stats.StatDto;
import org.example.petstore.service.order.OrderService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrdersTodayStatisticProvider implements StatisticsProvider {

    private final OrderService orderService;

    @Override
    public StatDto getStatistic() {
        return orderService.getOrderStats();
    }
}
