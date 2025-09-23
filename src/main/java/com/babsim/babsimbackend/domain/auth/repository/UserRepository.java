package com.babsim.babsimbackend.domain.auth.repository;

import com.babsim.babsimbackend.domain.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// AI 생성: 사용자 데이터 접근 리포지토리
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}
