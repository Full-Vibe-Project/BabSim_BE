package com.babsim.babsimbackend.domain.health.entity;

import com.babsim.babsimbackend.common.entity.BaseEntity;
import com.babsim.babsimbackend.domain.health.enums.RiskLevel;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// AI 생성: 위험 평가 엔티티
@Entity
@Table(name = "risk_assessments")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RiskAssessment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String riskName; // 예: "나트륨 과다 섭취 위험"

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RiskLevel riskLevel;

    @Lob
    private String description;

    @Builder
    public RiskAssessment(Long userId, String riskName, RiskLevel riskLevel, String description) {
        this.userId = userId;
        this.riskName = riskName;
        this.riskLevel = riskLevel;
        this.description = description;
    }
}
