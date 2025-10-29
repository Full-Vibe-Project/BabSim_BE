package com.babsim.babsimbackend.domain.timeseries.aggregation;

import com.babsim.babsimbackend.domain.user.entity.User;
import com.babsim.babsimbackend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class DailyAggregationScheduler {

    private final UserRepository userRepository;
    private final DailyNutritionAggregationService dailyNutritionAggregationService;

    @Scheduled(cron = "0 0 1 * * ?") // 매일 새벽 1시에 실행
    public void scheduleDailyAggregation() {
        log.info("Starting daily nutrition aggregation for previous day: {}", LocalDate.now().minusDays(1));
        LocalDate previousDay = LocalDate.now().minusDays(1);
        List<User> users = userRepository.findAll();

        for (User user : users) {
            try {
                dailyNutritionAggregationService.aggregateAndSave(user, previousDay);
                log.debug("Aggregated and saved nutrition for user {} on {}", user.getId(), previousDay);
            } catch (Exception e) {
                log.error("Failed to aggregate nutrition for user {} on {}: {}", user.getId(), previousDay, e.getMessage(), e);
            }
        }
        log.info("Finished daily nutrition aggregation.");
    }
}
