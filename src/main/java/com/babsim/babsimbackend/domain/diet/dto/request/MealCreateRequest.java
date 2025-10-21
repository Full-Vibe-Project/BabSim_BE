package com.babsim.babsimbackend.domain.diet.dto.request;

import com.babsim.babsimbackend.domain.diet.enums.MealType;
import java.util.List;
import java.util.UUID;

// AI 생성: 식단 생성을 위한 요청 데이터를 담는 DTO (record로 리팩토링)
public record MealCreateRequest(
    UUID userId,
    MealType mealType,
    String imageUrl,
    List<FoodItem> foods
) {
    public record FoodItem(
        String foodCode,
        Integer quantity
    ) {}
}
