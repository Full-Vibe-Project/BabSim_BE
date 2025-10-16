package com.babsim.babsimbackend.domain.diet.dto.response;

import com.babsim.babsimbackend.domain.diet.entity.DietEntry;
import com.babsim.babsimbackend.domain.diet.enums.MealType;
import lombok.Builder;
import lombok.Getter;

// AI 생성: 식단 기록 응답 DTO
@Getter
public class DietEntryResponse {
    private final Long id;
    private final MealType mealType;
    private final String foodName;
    private final double calories;

    @Builder
    public DietEntryResponse(Long id, MealType mealType, String foodName, double calories) {
        this.id = id;
        this.mealType = mealType;
        this.foodName = foodName;
        this.calories = calories;
    }

    public static DietEntryResponse from(DietEntry dietEntry) {
        return DietEntryResponse.builder()
                .id(dietEntry.getId())
                .mealType(dietEntry.getMealType())
                .foodName(dietEntry.getFood().getName())
//                .calories(dietEntry.getFood().getCalories())
                .build();
    }
}
