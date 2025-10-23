package com.babsim.babsimbackend.domain.timeseries.aggregation;

import com.babsim.babsimbackend.domain.diet.dto.response.DailyNutritionSummaryResponse;
import com.babsim.babsimbackend.domain.diet.repository.DailyNutritionSummaryRepository;
import com.babsim.babsimbackend.domain.timeseries.NutritionTimeseries;
import com.babsim.babsimbackend.domain.timeseries.NutritionTimeseriesRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("DailyNutritionAggregationService 테스트")
class DailyNutritionAggregationServiceTest {

    @Mock
    private NutritionTimeseriesRepository nutritionTimeseriesRepository;

    @Mock
    private DailyNutritionSummaryRepository dailyNutritionSummaryRepository;

    @InjectMocks
    private DailyNutritionAggregationService dailyNutritionAggregationService;

    @BeforeEach
    void setUp() {
        // 테스트에 필요한 초기 설정
    }

    @Test
    @DisplayName("given 전날 영양 데이터가 존재할 때 when 일별 영양 데이터 집계 요청하면 then TimescaleDB에 저장 성공")
    void givenDailyNutritionDataExists_whenAggregateDailyNutritionData_thenSaveToTimescaleDB() {
        // given
        LocalDate yesterday = LocalDate.now().minusDays(1);
        DailyNutritionSummaryResponse summaryResponse = new DailyNutritionSummaryResponse(
                1L, yesterday, 2000.0, 100.0, 250.0, 70.0
        );
        List<DailyNutritionSummaryResponse> mockSummaries = List.of(summaryResponse);

        when(dailyNutritionSummaryRepository.findDailyNutritionSummaryByDate(yesterday))
                .thenReturn(mockSummaries);

        // when
        dailyNutritionAggregationService.aggregateDailyNutritionData();

        // then
        verify(nutritionTimeseriesRepository, times(1)).saveAll(anyList());
        // 추가적인 검증: 저장된 데이터의 내용 확인 (예: userId, energy 등)
        // ArgumentCaptor<List<NutritionTimeseries>> captor = ArgumentCaptor.forClass(List.class);
        // verify(nutritionTimeseriesRepository).saveAll(captor.capture());
        // List<NutritionTimeseries> savedEntities = captor.getValue();
        // assertThat(savedEntities).hasSize(1);
        // assertThat(savedEntities.get(0).getUserId()).isEqualTo(1L);
    }

    @Test
    @DisplayName("given 전날 영양 데이터가 없을 때 when 일별 영양 데이터 집계 요청하면 then TimescaleDB에 저장하지 않음")
    void givenNoDailyNutritionData_whenAggregateDailyNutritionData_thenDoNotSaveToTimescaleDB() {
        // given
        LocalDate yesterday = LocalDate.now().minusDays(1);
        when(dailyNutritionSummaryRepository.findDailyNutritionSummaryByDate(yesterday))
                .thenReturn(Collections.emptyList());

        // when
        dailyNutritionAggregationService.aggregateDailyNutritionData();

        // then
        verify(nutritionTimeseriesRepository, never()).saveAll(anyList());
    }
}
