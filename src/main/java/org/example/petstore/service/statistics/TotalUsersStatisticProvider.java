package org.example.petstore.service.statistics;

import lombok.RequiredArgsConstructor;
import org.example.petstore.dto.stats.StatDto;
import org.example.petstore.service.user.UserService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TotalUsersStatisticProvider implements StatisticsProvider {

    private final UserService userService;
    @Override
    public StatDto getStatistic() {
        return userService.getUsersStats();
    }
}
