package com.babsim.babsimbackend.domain.timeseries.aggregation;

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

// AI 생성: 일별 영양 데이터를 집계하여 TimescaleDB에 저장하는 서비스
@Service
@RequiredArgsConstructor
@Slf4j
public class DailyNutritionAggregationService {

    private final NutritionTimeseriesRepository nutritionTimeseriesRepository;
    // TODO: MySQL에서 일별 영양 데이터를 조회하는 서비스 또는 리포지토리 주입 필요
    // private final DailyNutritionSummaryRepository dailyNutritionSummaryRepository; 

    /**
     * 매일 자정에 실행되어 전날의 영양 데이터를 집계하고 TimescaleDB에 저장합니다.
     */
    //@Scheduled(cron = "0 0 0 * * ?") // 매일 자정 실행
    @Transactional("timescaledbTransactionManager") // TimescaleDB 트랜잭션 매니저 사용
    public void aggregateDailyNutritionData() {
        LocalDate yesterday = LocalDate.now().minusDays(1);
        log.info("[{}] 전날({})의 영양 데이터 집계를 시작합니다.", getClass().getSimpleName(), yesterday);

        // TODO: MySQL에서 전날의 사용자별 일별 영양 데이터를 조회하는 로직 구현
        // List<DailyNutritionSummary> dailySummaries = dailyNutritionSummaryRepository.findByDate(yesterday);

        // 임시 데이터 (실제 구현 시 위 TODO 로직으로 대체)
        List<NutritionTimeseries> aggregatedData = List.of(
                NutritionTimeseries.builder()
                        .userId(1L)
                        .ts(LocalDateTime.now())
                        .energy(2000.0)
                        .protein(100.0)
                        .carb(250.0)
                        .fat(70.0)
                        .weight(70.5)
                        .bloodSugar(90.0)
                        .build()
        );

        if (!aggregatedData.isEmpty()) {
            nutritionTimeseriesRepository.saveAll(aggregatedData);
            log.info("[{}] 전날({})의 영양 데이터 {}건을 TimescaleDB에 저장했습니다.", getClass().getSimpleName(), yesterday, aggregatedData.size());
        } else {
            log.info("[{}] 전날({})의 집계할 영양 데이터가 없습니다.", getClass().getSimpleName(), yesterday);
        }
    }
}
