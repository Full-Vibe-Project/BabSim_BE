package com.babsim.babsimbackend.domain.timeseries;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.UUID;

// AI 생성: TimescaleDB 시계열 영양 데이터 엔티티
@Entity
@Table(name = "nutrition_timeseries")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NutritionTimeseries {

    @EmbeddedId
    private NutritionTimeseriesId id;

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
