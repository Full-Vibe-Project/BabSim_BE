package com.babsim.babsimbackend.domain.diet.entity;

import com.babsim.babsimbackend.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// AI 생성: 영양소 엔티티
@Entity
@Table(name = "nutrients")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Nutrient extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private double carbohydrate; // 탄수화물
    private double protein;      // 단백질
    private double fat;          // 지방
    private double sugar;        // 당류
    private double sodium;       // 나트륨

    @Builder
    public Nutrient(double carbohydrate, double protein, double fat, double sugar, double sodium) {
        this.carbohydrate = carbohydrate;
        this.protein = protein;
        this.fat = fat;
        this.sugar = sugar;
        this.sodium = sodium;
    }
}
