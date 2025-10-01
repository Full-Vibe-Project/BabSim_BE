package com.babsim.babsimbackend.common.exception;

import lombok.Getter;

// AI 생성: 비즈니스 예외 클래스
@Getter
public class BusinessException extends RuntimeException {

    private final ErrorCode errorCode;

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}
