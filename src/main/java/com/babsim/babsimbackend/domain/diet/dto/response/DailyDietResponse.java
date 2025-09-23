package com.babsim.babsimbackend.domain.diet.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.util.List;

// AI 생성: 일일 식단 응답 DTO
@Getter
public class DailyDietResponse {
    private final LocalDate date;
    private final List<DietEntryResponse> meals;
    private final double totalCalories;

    @Builder
    public DailyDietResponse(LocalDate date, List<DietEntryResponse> meals, double totalCalories) {
        this.date = date;
        this.meals = meals;
        this.totalCalories = totalCalories;
    }
}
