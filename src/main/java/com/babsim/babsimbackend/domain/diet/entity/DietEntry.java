package com.babsim.babsimbackend.domain.diet.entity;

import com.babsim.babsimbackend.common.entity.BaseEntity;
import com.babsim.babsimbackend.domain.diet.enums.MealType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// AI 생성: 식단 기록 엔티티
@Entity
@Table(name = "diet_entries")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DietEntry extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MealType mealType;

    @Column
    private String imageUrl;

    // DietEntry와 Food는 다대다 관계가 될 수 있으나, 단순화를 위해 우선 DietEntry가 Food 정보를 직접 포함하도록 설계
    // 추후 확장 시 DietFood 같은 중간 테이블 엔티티를 고려할 수 있음
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "food_id")
    private Food food;

    @Builder
    public DietEntry(Long userId, MealType mealType, String imageUrl, Food food) {
        this.userId = userId;
        this.mealType = mealType;
        this.imageUrl = imageUrl;
        this.food = food;
    }
}
