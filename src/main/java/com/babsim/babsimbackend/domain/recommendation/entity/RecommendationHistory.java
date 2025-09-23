package com.babsim.babsimbackend.domain.recommendation.entity;

import com.babsim.babsimbackend.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// AI 생성: 추천 이력 엔티티
@Entity
@Table(name = "recommendation_histories")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RecommendationHistory extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long menuId;

    @Column(nullable = false)
    private String menuName;

    @Builder
    public RecommendationHistory(Long userId, Long menuId, String menuName) {
        this.userId = userId;
        this.menuId = menuId;
        this.menuName = menuName;
    }
}
