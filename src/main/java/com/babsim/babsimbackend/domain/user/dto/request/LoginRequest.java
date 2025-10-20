package com.babsim.babsimbackend.domain.user.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

// AI 생성: 로그인 요청 DTO
@Getter
@NoArgsConstructor
public class LoginRequest {
    private String email;
    private String password;
}
