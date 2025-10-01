package com.babsim.babsimbackend.domain.health.entity;

import com.babsim.babsimbackend.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

// AI 생성: 건강 요약 엔티티
@Entity
@Table(name = "health_summaries")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HealthSummary extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    // TODO: 요약 데이터 필드 추가 (예: 평균 칼로리, 총 섭취 영양소 등)
    private double averageCalories;

    @Lob
    private String aiSummary;

    @Builder
    public HealthSummary(Long userId, LocalDate startDate, LocalDate endDate, double averageCalories, String aiSummary) {
        this.userId = userId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.averageCalories = averageCalories;
        this.aiSummary = aiSummary;
    }
}
