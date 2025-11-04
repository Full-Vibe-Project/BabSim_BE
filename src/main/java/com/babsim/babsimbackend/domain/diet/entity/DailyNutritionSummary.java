package com.babsim.babsimbackend.domain.diet.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.Subselect;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;

import java.time.LocalDate;
import java.util.UUID;

// AI 생성: 일일 영양소 합계를 나타내는 뷰 엔티티
@Entity
@Immutable // 뷰는 변경 불가능하다고 가정
@Subselect(
    "SELECT m.user_id as user_id, CAST(m.created_at AS date) as date, " +
    "SUM(f.energy * mf.quantity) as energy, " +
    "SUM(f.protein * mf.quantity) as protein, " +
    "SUM(f.carb * mf.quantity) as carb, " +
    "SUM(f.fat * mf.quantity) as fat " +
    "FROM meal m " +
    "JOIN meal_food mf ON m.id = mf.meal_id " +
    "JOIN food f ON mf.food_code = f.code " +
    "GROUP BY m.user_id, CAST(m.created_at AS date)"
)
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DailyNutritionSummary {

    @EmbeddedId
    private DailyNutritionSummaryId id;

    @Column(nullable = false)
    private Double energy;

    @Column(nullable = false)
    private Double protein;

    @Column(nullable = false)
    private Double carb;

    @Column(nullable = false)
    private Double fat;

    // Helper methods to access composite key components
    public UUID getUserId() {
        return this.id.getUserId();
    }

    public LocalDate getDate() {
        return this.id.getDate();
    }
}
