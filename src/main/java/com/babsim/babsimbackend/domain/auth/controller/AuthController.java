package com.babsim.babsimbackend.domain.auth.controller;

import com.babsim.babsimbackend.common.dto.BaseResponse;
import com.babsim.babsimbackend.domain.auth.dto.request.LoginRequest;
import com.babsim.babsimbackend.domain.auth.dto.request.RegisterRequest;
import com.babsim.babsimbackend.domain.auth.dto.response.AuthResponse;
import com.babsim.babsimbackend.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// AI 생성: 인증/사용자 관리 컨트롤러
@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<BaseResponse<Void>> register(@RequestBody RegisterRequest request) {
        // TODO: 회원가입 로직 구현
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/login")
    public ResponseEntity<BaseResponse<AuthResponse>> login(@RequestBody LoginRequest request) {
        // TODO: 로그인 로직 구현
        return ResponseEntity.ok(null);
    }
}
