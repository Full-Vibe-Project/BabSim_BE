package com.babsim.babsimbackend.domain.diet.dto.response;

import com.babsim.babsimbackend.domain.diet.entity.Food;

// AI 생성: 음식 정보 조회의 응답 데이터를 담는 DTO (record로 리팩토링)
public record FoodResponse(
    String code,
    String name,
    Double energy,
    Double carb,
    Double protein,
    Double fat,
    String weight
) {
    public FoodResponse(Food entity) {
        this(entity.getCode(),
             entity.getName(),
             entity.getEnergy(),
             entity.getCarb(),
             entity.getProtein(),
             entity.getFat(),
             entity.getWeight());
    }
}
