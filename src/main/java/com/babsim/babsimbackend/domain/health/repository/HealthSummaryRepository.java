package com.babsim.babsimbackend.domain.health.repository;

import com.babsim.babsimbackend.domain.health.entity.HealthSummary;
import org.springframework.data.jpa.repository.JpaRepository;

// AI 생성: 건강 요약 데이터 접근 리포지토리
public interface HealthSummaryRepository extends JpaRepository<HealthSummary, Long> {
}
