package com.babsim.babsimbackend.domain.auth.service;

import com.babsim.babsimbackend.domain.auth.dto.request.LoginRequest;
import com.babsim.babsimbackend.domain.auth.dto.request.RegisterRequest;
import com.babsim.babsimbackend.domain.auth.dto.response.AuthResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// AI 생성: 인증 비즈니스 로직 서비스
@Service
@RequiredArgsConstructor
public class AuthService {

    // private final UserRepository userRepository;
    // private final PasswordEncoder passwordEncoder;

    public void register(RegisterRequest request) {
        // TODO: 회원가입 로직 구현
    }

    public AuthResponse login(LoginRequest request) {
        // TODO: 로그인 로직 구현
        return null;
    }
}
