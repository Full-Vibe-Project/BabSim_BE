package com.babsim.babsimbackend.domain.diet.dto.response;

import com.babsim.babsimbackend.domain.diet.entity.Food;
import lombok.Getter;

// AI 생성: 음식 정보 조회의 응답 데이터를 담는 DTO
@Getter
public class FoodResponse {

    private final String code;
    private final String name;
    private final Double energy;
    private final Double carb;
    private final Double protein;
    private final Double fat;
    private final String weight;

    public FoodResponse(Food entity) {
        this.code = entity.getCode();
        this.name = entity.getName();
        this.energy = entity.getEnergy();
        this.carb = entity.getCarb();
        this.protein = entity.getProtein();
        this.fat = entity.getFat();
        this.weight = entity.getWeight();
    }
}
