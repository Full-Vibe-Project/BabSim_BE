package com.babsim.babsimbackend.domain.diet.dto.request;

import com.babsim.babsimbackend.domain.diet.enums.MealType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

// AI 생성: 식단 기록 요청 DTO
@Getter
@NoArgsConstructor
public class DietEntryRequest {
    private LocalDate date;
    private MealType mealType;
    private String foodName;
    private double calories;
    // TODO: nutrients 관련 필드 추가 필요
}
