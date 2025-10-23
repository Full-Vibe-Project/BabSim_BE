package com.babsim.babsimbackend.domain.timeseries.aggregation;

import com.babsim.babsimbackend.domain.diet.dto.response.DailyNutritionSummaryResponse;
import com.babsim.babsimbackend.domain.diet.repository.DailyNutritionSummaryRepository;
import com.babsim.babsimbackend.domain.timeseries.NutritionTimeseries;
import com.babsim.babsimbackend.domain.timeseries.NutritionTimeseriesRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

// AI 생성: 일별 영양 데이터를 집계하여 TimescaleDB에 저장하는 서비스
@Service
@RequiredArgsConstructor
@Slf4j
public class DailyNutritionAggregationService {

    private final NutritionTimeseriesRepository nutritionTimeseriesRepository;
    private final DailyNutritionSummaryRepository dailyNutritionSummaryRepository;

    /**
     * 매일 자정에 실행되어 전날의 영양 데이터를 집계하고 TimescaleDB에 저장합니다.
     */
    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정 실행
    @Transactional("timescaledbTransactionManager") // TimescaleDB 트랜잭션 매니저 사용
    public void aggregateDailyNutritionData() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        log.info("[{}] 전날({})의 영양 데이터 집계를 시작합니다.", getClass().getSimpleName(), yesterday);

        List<DailyNutritionSummaryResponse> dailySummaries = dailyNutritionSummaryRepository.findDailyNutritionSummaryByDate(yesterday);

        if (!dailySummaries.isEmpty()) {
            List<NutritionTimeseries> aggregatedData = dailySummaries.stream()
                    .map(summary -> NutritionTimeseries.builder()
                            .userId(summary.userId())
                            .ts(LocalDateTime.now()) // 집계 시점
                            .energy(summary.totalEnergy())
                            .protein(summary.totalProtein())
                            .carb(summary.totalCarb())
                            .fat(summary.totalFat())
                            .build())
                    .collect(Collectors.toList());

            nutritionTimeseriesRepository.saveAll(aggregatedData);
            log.info("[{}] 전날({})의 영양 데이터 {}건을 TimescaleDB에 저장했습니다.", getClass().getSimpleName(), yesterday, aggregatedData.size());
        } else {
            log.info("[{}] 전날({})의 집계할 영양 데이터가 없습니다.", getClass().getSimpleName(), yesterday);
        }
    }
}