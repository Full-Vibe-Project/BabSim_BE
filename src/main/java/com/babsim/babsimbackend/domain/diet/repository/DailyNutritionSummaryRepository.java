package com.babsim.babsimbackend.domain.diet.repository;

import com.babsim.babsimbackend.domain.diet.entity.DailyNutritionSummary;
import com.babsim.babsimbackend.domain.diet.entity.DailyNutritionSummaryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// AI 생성: DailyNutritionSummary 뷰 엔티티의 데이터 접근을 위한 Repository 인터페이스
@Repository
public interface DailyNutritionSummaryRepository extends JpaRepository<DailyNutritionSummary, DailyNutritionSummaryId> {
}