package com.babsim.babsimbackend.domain.diet.entity;

import com.babsim.babsimbackend.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// AI 생성: 음식 정보 엔티티
@Entity
@Table(name = "foods")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Food extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    // 1인분 기준 g
    private double servingSize;

    private double calories;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "nutrient_id")
    private Nutrient nutrient;

    @Builder
    public Food(String name, double servingSize, double calories, Nutrient nutrient) {
        this.name = name;
        this.servingSize = servingSize;
        this.calories = calories;
        this.nutrient = nutrient;
    }
}
