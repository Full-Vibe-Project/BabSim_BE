package com.babsim.babsimbackend.domain.diet.dto.response;

import com.babsim.babsimbackend.domain.diet.entity.Meal;
import com.babsim.babsimbackend.domain.diet.enums.MealType;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

// AI 생성: 식단 정보 조회의 응답 데이터를 담는 DTO
@Getter
public class MealResponse {

    private final Long id;
    private final MealType mealType;
    private final String imageUrl;
    private final LocalDateTime createdAt;
    private final List<FoodInfo> foods;

    public MealResponse(Meal entity) {
        this.id = entity.getId();
        this.mealType = entity.getMealType();
        this.imageUrl = entity.getImageUrl();
        this.createdAt = entity.getCreatedAt();
        this.foods = entity.getMealFoods().stream()
            .map(FoodInfo::new)
            .collect(Collectors.toList());
    }

    @Getter
    public static class FoodInfo {
        private final String foodCode;
        private final String foodName;
        private final Integer quantity;

        public FoodInfo(com.babsim.babsimbackend.domain.diet.entity.MealFood mealFood) {
            this.foodCode = mealFood.getFood().getCode();
            this.foodName = mealFood.getFood().getName();
            this.quantity = mealFood.getQuantity();
        }
    }
}
