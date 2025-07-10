package org.example.petstore.service.user;

import jakarta.persistence.NoResultException;
import lombok.RequiredArgsConstructor;
import org.example.petstore.dto.stats.StatDto;
import org.example.petstore.model.User;
import org.example.petstore.repository.UserRepository;
import org.example.petstore.utils.StatUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final StatUtils statUtils;

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NoResultException("User not found"));
    }

    public StatDto getUsersStats() {
        LocalDate today = LocalDate.now();

        LocalDateTime todayEnd = statUtils.getEndOfDayExclusive(today);
        LocalDateTime weekAgoEnd = statUtils.getEndOfDayExclusive(today.minusWeeks(1));

        long userCountNow = userRepository.countByCreatedAtBefore(todayEnd);
        long userCountLastWeek = userRepository.countByCreatedAtBefore(weekAgoEnd);

        int change = statUtils.calculatePercentageChange(userCountNow, userCountLastWeek);

        return new StatDto("Total Users", (int) userCountNow, change, "percent", null);
    }

}
