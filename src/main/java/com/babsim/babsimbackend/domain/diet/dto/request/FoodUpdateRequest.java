package com.babsim.babsimbackend.domain.diet.dto.request;

// AI 생성: 음식 정보 수정을 위한 요청 데이터를 담는 DTO (record로 리팩토링)
public record FoodUpdateRequest(
    String name,
    Double energy,
    Double carb,
    Double protein,
    Double fat,
    String weight
) {}
