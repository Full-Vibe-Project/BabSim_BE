package com.babsim.babsimbackend.domain.timeseries.aggregation;

import com.babsim.babsimbackend.domain.diet.dto.response.DailyNutritionSumDto;
import com.babsim.babsimbackend.domain.diet.repository.MealRepository;
import com.babsim.babsimbackend.domain.timeseries.NutritionTimeseries;
import com.babsim.babsimbackend.domain.timeseries.NutritionTimeseriesId;
import com.babsim.babsimbackend.domain.timeseries.NutritionTimeseriesRepository;
import com.babsim.babsimbackend.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DailyNutritionAggregationService {

    private final MealRepository mealRepository;
    private final NutritionTimeseriesRepository nutritionTimeseriesRepository;

    @Transactional("timescaledbTransactionManager")
    public void aggregateAndSave(User user, LocalDate date) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.plusDays(1).atStartOfDay();

        DailyNutritionSumDto nutritionSum = mealRepository.findDailyNutritionSumByUserIdAndDate(user.getId(), startOfDay, endOfDay);

        Double energy = Optional.ofNullable(nutritionSum).map(DailyNutritionSumDto::totalEnergy).orElse(0.0);
        Double protein = Optional.ofNullable(nutritionSum).map(DailyNutritionSumDto::totalProtein).orElse(0.0);
        Double carb = Optional.ofNullable(nutritionSum).map(DailyNutritionSumDto::totalCarb).orElse(0.0);
        Double fat = Optional.ofNullable(nutritionSum).map(DailyNutritionSumDto::totalFat).orElse(0.0);

        NutritionTimeseries series = NutritionTimeseries.builder()
            .id(new NutritionTimeseriesId(user.getId(), startOfDay))
            .energy(energy)
            .protein(protein)
            .carb(carb)
            .fat(fat)
            .build();

        nutritionTimeseriesRepository.save(series);
    }
}
