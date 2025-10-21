package com.babsim.babsimbackend.domain.diet.entity;

import com.babsim.babsimbackend.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// AI 생성: 식단(Meal)과 음식(Food)의 관계를 나타내는 조인 엔티티
@Entity
@Table(name = "meal_food")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MealFood extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "meal_id", nullable = false)
    private Meal meal;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_code", nullable = false)
    private Food food;

    @Column(name = "quantity")
    private Integer quantity;

    @Builder
    public MealFood(Meal meal, Food food, Integer quantity) {
        this.meal = meal;
        this.food = food;
        this.quantity = quantity;
    }
}
