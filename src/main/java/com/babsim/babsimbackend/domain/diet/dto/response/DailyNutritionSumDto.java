package com.babsim.babsimbackend.domain.diet.dto.response;

import lombok.Builder;

@Builder
public record DailyNutritionSumDto(
    Double totalEnergy,
    Double totalProtein,
    Double totalCarb,
    Double totalFat
) {
}
