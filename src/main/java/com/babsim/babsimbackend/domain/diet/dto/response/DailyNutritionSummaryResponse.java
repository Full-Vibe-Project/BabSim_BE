package com.babsim.babsimbackend.domain.diet.dto.response;

import java.time.LocalDate;

// AI 생성: 일별 영양소 합계 응답 DTO
public record DailyNutritionSummaryResponse(
        Long userId,
        LocalDate date,
        Double totalEnergy,
        Double totalProtein,
        Double totalCarb,
        Double totalFat
) {
}
