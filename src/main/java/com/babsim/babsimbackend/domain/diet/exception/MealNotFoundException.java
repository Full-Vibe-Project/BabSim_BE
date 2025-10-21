package com.babsim.babsimbackend.domain.diet.exception;

// AI 생성: 특정 ID로 식단을 찾을 수 없을 때 발생하는 예외
public class MealNotFoundException extends RuntimeException {

    public MealNotFoundException(Long mealId) {
        super("해당 ID의 식단 정보를 찾을 수 없습니다: " + mealId);
    }
}
