package com.babsim.babsimbackend.domain.auth.service;

import com.babsim.babsimbackend.domain.auth.entity.User;
import com.babsim.babsimbackend.domain.auth.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// AI 생성: 사용자 관리 로직 서비스
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User findById(Long userId) {
        // TODO: 사용자 조회 로직 구현
        return null;
    }
}
