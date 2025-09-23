package com.babsim.babsimbackend.domain.diet.dto.response;

import com.babsim.babsimbackend.domain.diet.entity.Nutrient;
import lombok.Builder;
import lombok.Getter;

// AI 생성: 영양소 정보 응답 DTO
@Getter
public class NutrientResponse {
    private final double carbohydrate;
    private final double protein;
    private final double fat;
    private final double sugar;
    private final double sodium;

    @Builder
    public NutrientResponse(double carbohydrate, double protein, double fat, double sugar, double sodium) {
        this.carbohydrate = carbohydrate;
        this.protein = protein;
        this.fat = fat;
        this.sugar = sugar;
        this.sodium = sodium;
    }

    public static NutrientResponse from(Nutrient nutrient) {
        return NutrientResponse.builder()
                .carbohydrate(nutrient.getCarbohydrate())
                .protein(nutrient.getProtein())
                .fat(nutrient.getFat())
                .sugar(nutrient.getSugar())
                .sodium(nutrient.getSodium())
                .build();
    }
}
