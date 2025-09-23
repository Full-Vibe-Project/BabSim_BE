package com.babsim.babsimbackend.domain.diet.repository;

import com.babsim.babsimbackend.domain.diet.entity.Nutrient;
import org.springframework.data.jpa.repository.JpaRepository;

// AI 생성: 영양소 정보 데이터 접근 리포지토리
public interface NutrientRepository extends JpaRepository<Nutrient, Long> {
}
