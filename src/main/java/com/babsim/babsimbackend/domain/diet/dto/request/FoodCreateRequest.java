package com.babsim.babsimbackend.domain.diet.dto.request;

import com.babsim.babsimbackend.domain.diet.entity.Food;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// AI 생성: 음식 정보 생성을 위한 요청 데이터를 담는 DTO
@Getter
@NoArgsConstructor
public class FoodCreateRequest {

    private String code;
    private String name;
    private Double energy;
    private Double carb;
    private Double protein;
    private Double fat;
    private String weight;

    @Builder
    public FoodCreateRequest(String code, String name, Double energy, Double carb, Double protein, Double fat, String weight) {
        this.code = code;
        this.name = name;
        this.energy = energy;
        this.carb = carb;
        this.protein = protein;
        this.fat = fat;
        this.weight = weight;
    }

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
