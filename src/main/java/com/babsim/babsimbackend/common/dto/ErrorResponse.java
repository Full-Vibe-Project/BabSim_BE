package com.babsim.babsimbackend.common.dto;

import com.babsim.babsimbackend.common.exception.ErrorCode;
import lombok.Getter;

// AI 생성: 에러 응답 DTO
@Getter
public class ErrorResponse {
    private final int status;
    private final String message;
    private final String code;

    public ErrorResponse(ErrorCode errorCode) {
        this.status = errorCode.getStatus().value();
        this.message = errorCode.getMessage();
        this.code = errorCode.name();
    }
}
