package com.babsim.babsimbackend.domain.diet.exception;

// AI 생성: 특정 코드로 음식을 찾을 수 없을 때 발생하는 예외
public class FoodNotFoundException extends RuntimeException {

    public FoodNotFoundException(String code) {
        super("해당 코드의 음식 정보를 찾을 수 없습니다: " + code);
    }
}
