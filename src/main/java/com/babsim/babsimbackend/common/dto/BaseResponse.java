package com.babsim.babsimbackend.common.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

// AI 생성: 공통 응답 DTO
@Getter
@RequiredArgsConstructor
public class BaseResponse<T> {
    private final int status;
    private final String message;
    private final T data;
}
