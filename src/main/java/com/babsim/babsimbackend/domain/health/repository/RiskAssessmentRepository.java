package com.babsim.babsimbackend.domain.health.repository;

import com.babsim.babsimbackend.domain.health.entity.RiskAssessment;
import org.springframework.data.jpa.repository.JpaRepository;

// AI 생성: 위험 평가 데이터 접근 리포지토리
public interface RiskAssessmentRepository extends JpaRepository<RiskAssessment, Long> {
}
