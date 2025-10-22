package com.babsim.babsimbackend.domain.diet.dto.response;

import com.babsim.babsimbackend.domain.diet.entity.Meal;
import com.babsim.babsimbackend.domain.diet.entity.MealFood;
import com.babsim.babsimbackend.domain.diet.enums.MealType;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

// AI 생성: 식단 정보 조회의 응답 데이터를 담는 DTO (record로 리팩토링)
public record MealResponse(
    Long id,
    MealType mealType,
    String imageUrl,
    LocalDateTime createdAt,
    List<FoodInfo> foods
) {
    public MealResponse(Meal entity) {
        this(
            entity.getId(),
            entity.getMealType(),
            entity.getImageUrl(),
            entity.getCreatedAt(),
            entity.getMealFoods().stream()
                .map(FoodInfo::new)
                .collect(Collectors.toList())
        );
    }

    public record FoodInfo(
        String foodCode,
        String foodName,
        Integer quantity
    ) {
        public FoodInfo(MealFood mealFood) {
            this(mealFood.getFood().getCode(), mealFood.getFood().getName(), mealFood.getQuantity());
        }
    }
}
