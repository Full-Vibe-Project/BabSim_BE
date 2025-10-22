package com.babsim.babsimbackend.domain.diet.dto.request;

import com.babsim.babsimbackend.domain.diet.entity.Food;

// AI 생성: 음식 정보 생성을 위한 요청 데이터를 담는 DTO (record로 리팩토링)
public record FoodCreateRequest(
    String code,
    String name,
    Double energy,
    Double carb,
    Double protein,
    Double fat,
    String weight
) {
    public Food toEntity() {
        return Food.builder()
            .code(code)
            .name(name)
            .energy(energy)
            .carb(carb)
            .protein(protein)
            .fat(fat)
            .weight(weight)
            .build();
    }
}
