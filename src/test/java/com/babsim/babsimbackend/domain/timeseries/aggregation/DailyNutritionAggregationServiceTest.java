package com.babsim.babsimbackend.domain.timeseries.aggregation;

import com.babsim.babsimbackend.domain.diet.dto.response.DailyNutritionSumDto;
import com.babsim.babsimbackend.domain.diet.repository.MealRepository;
import com.babsim.babsimbackend.domain.timeseries.NutritionTimeseries;
import com.babsim.babsimbackend.domain.timeseries.NutritionTimeseriesId;
import com.babsim.babsimbackend.domain.timeseries.NutritionTimeseriesRepository;
import com.babsim.babsimbackend.domain.user.entity.User;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DailyNutritionAggregationServiceTest {

    @InjectMocks
    private DailyNutritionAggregationService dailyNutritionAggregationService;

    @Mock
    private MealRepository mealRepository;

    @Mock
    private NutritionTimeseriesRepository nutritionTimeseriesRepository;

    @Test
    @DisplayName("일일 영양 정보를 집계하고 TimescaleDB에 저장한다")
    void givenDailyNutritionData_whenAggregateAndSave_thenSaveIsCalledWithCorrectValues() {
        // given
        User user = User.builder().name("testuser").build();
        UUID userId = UUID.randomUUID();
        ReflectionTestUtils.setField(user, "id", userId);

        LocalDate date = LocalDate.of(2025, 10, 29);
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.plusDays(1).atStartOfDay();

        DailyNutritionSumDto nutritionSum = DailyNutritionSumDto.builder()
            .totalEnergy(2000.0)
            .totalProtein(80.0)
            .totalCarb(250.0)
            .totalFat(60.0)
            .build();

        when(mealRepository.findDailyNutritionSumByUserIdAndDate(user.getId(), startOfDay, endOfDay))
            .thenReturn(nutritionSum);

        // when
        dailyNutritionAggregationService.aggregateAndSave(user, date);

        // then
        ArgumentCaptor<NutritionTimeseries> captor = ArgumentCaptor.forClass(NutritionTimeseries.class);
        verify(nutritionTimeseriesRepository).save(captor.capture());
        NutritionTimeseries savedEntity = captor.getValue();

        assertThat(savedEntity.getId().getUserId()).isEqualTo(user.getId());
        assertThat(savedEntity.getId().getTs()).isEqualTo(startOfDay);
        assertThat(savedEntity.getEnergy()).isEqualTo(2000.0);
        assertThat(savedEntity.getProtein()).isEqualTo(80.0);
        assertThat(savedEntity.getCarb()).isEqualTo(250.0);
        assertThat(savedEntity.getFat()).isEqualTo(60.0);
    }
}
