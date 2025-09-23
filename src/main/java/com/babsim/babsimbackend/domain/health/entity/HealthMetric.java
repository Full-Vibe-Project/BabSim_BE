package com.babsim.babsimbackend.domain.health.entity;

import com.babsim.babsimbackend.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// AI 생성: 건강 지표 엔티티 (사용자 입력 기반)
@Entity
@Table(name = "health_metrics")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class HealthMetric extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    private double weight; // 체중
    private double height; // 신장
    private int bloodPressureSystolic; // 수축기 혈압
    private int bloodPressureDiastolic; // 이완기 혈압
    private int bloodSugar; // 혈당

    @Builder
    public HealthMetric(Long userId, double weight, double height, int bloodPressureSystolic, int bloodPressureDiastolic, int bloodSugar) {
        this.userId = userId;
        this.weight = weight;
        this.height = height;
        this.bloodPressureSystolic = bloodPressureSystolic;
        this.bloodPressureDiastolic = bloodPressureDiastolic;
        this.bloodSugar = bloodSugar;
    }
}
