package com.babsim.babsimbackend.domain.diet.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// AI 생성: 음식 정보 수정을 위한 요청 데이터를 담는 DTO
@Getter
@NoArgsConstructor
public class FoodUpdateRequest {

    private String name;
    private Double energy;
    private Double carb;
    private Double protein;
    private Double fat;
    private String weight;

    @Builder
    public FoodUpdateRequest(String name, Double energy, Double carb, Double protein, Double fat, String weight) {
        this.name = name;
        this.energy = energy;
        this.carb = carb;
        this.protein = protein;
        this.fat = fat;
        this.weight = weight;
    }
}
