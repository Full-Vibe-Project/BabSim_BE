package com.babsim.babsimbackend.domain.diet.dto.request;

import com.babsim.babsimbackend.domain.diet.enums.MealType;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

// AI 생성: 식단 생성을 위한 요청 데이터를 담는 DTO
@Getter
@NoArgsConstructor
public class MealCreateRequest {

    private UUID userId;
    private MealType mealType;
    private String imageUrl;
    private List<FoodItem> foods;

    @Builder
    public MealCreateRequest(UUID userId, MealType mealType, String imageUrl, List<FoodItem> foods) {
        this.userId = userId;
        this.mealType = mealType;
        this.imageUrl = imageUrl;
        this.foods = foods;
    }

    @Getter
    @NoArgsConstructor
    public static class FoodItem {
        private String foodCode;
        private Integer quantity;

        @Builder
        public FoodItem(String foodCode, Integer quantity) {
            this.foodCode = foodCode;
            this.quantity = quantity;
        }
    }
}
