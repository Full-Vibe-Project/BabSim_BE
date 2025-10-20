package com.babsim.babsimbackend.domain.user.dto.response;

import lombok.Builder;
import lombok.Getter;

// AI 생성: 인증 응답 DTO (토큰 포함)
@Getter
public class AuthResponse {
    private final String accessToken;

    @Builder
    public AuthResponse(String accessToken) {
        this.accessToken = accessToken;
    }
}
