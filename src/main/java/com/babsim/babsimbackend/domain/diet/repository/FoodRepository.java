package com.babsim.babsimbackend.domain.diet.repository;

import com.babsim.babsimbackend.domain.diet.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// AI 생성: Food 엔티티의 데이터 접근을 위한 Repository 인터페이스
public interface FoodRepository extends JpaRepository<Food, String> {
    Optional<Food> findByName(String name);
}
