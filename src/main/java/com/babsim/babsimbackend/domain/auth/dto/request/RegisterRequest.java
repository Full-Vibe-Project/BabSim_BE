package com.babsim.babsimbackend.domain.auth.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

// AI 생성: 회원가입 요청 DTO
@Getter
@NoArgsConstructor
public class RegisterRequest {
    private String email;
    private String password;
    private String name;
}
