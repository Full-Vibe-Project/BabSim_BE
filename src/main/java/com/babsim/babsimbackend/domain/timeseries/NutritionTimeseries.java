package com.babsim.babsimbackend.domain.timeseries;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;

// AI 생성: TimescaleDB 시계열 영양 데이터 엔티티
@Entity
@Table(name = "nutrition_timeseries")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NutritionTimeseries {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @CreationTimestamp
    @Column(name = "ts", nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private LocalDateTime ts;

    @Column(nullable = false)
    private Double energy;

    @Column(nullable = false)
    private Double protein;

    @Column(nullable = false)
    private Double carb;

    @Column(nullable = false)
    private Double fat;

    @Column
    private Double weight;

    @Column(name = "blood_sugar")
    private Double bloodSugar;
}
