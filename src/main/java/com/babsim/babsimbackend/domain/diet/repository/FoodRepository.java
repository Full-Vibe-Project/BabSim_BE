package com.babsim.babsimbackend.domain.diet.repository;

import com.babsim.babsimbackend.domain.diet.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// AI 생성: 음식 정보 데이터 접근 리포지토리
public interface FoodRepository extends JpaRepository<Food, Long> {
    Optional<Food> findByName(String name);
}
