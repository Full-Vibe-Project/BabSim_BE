package com.babsim.babsimbackend.domain.diet.entity;

import com.babsim.babsimbackend.domain.diet.dto.request.FoodUpdateRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

// AI 생성: 음식의 영양 정보를 담는 Food 엔티티
@Entity
@Table(name = "food") // 테이블 이름을 명시적으로 지정
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
public class Food {

    @Id
    @Column(name = "code", nullable = false, length = 30, unique = true)
    private String code;

    @Column(name = "name", length = 100)
    private String name;

    @Column(name = "energy")
    private Double energy;

    @Column(name = "carb")
    private Double carb;

    @Column(name = "protein")
    private Double protein;

    @Column(name = "fat")
    private Double fat;

    @Column(name = "weight", length = 20)
    private String weight;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Builder
    public Food(String code, String name, Double energy, Double carb, Double protein, Double fat, String weight) {
        this.code = code;
        this.name = name;
        this.energy = energy;
        this.carb = carb;
        this.protein = protein;
        this.fat = fat;
        this.weight = weight;
    }

    public void update(FoodUpdateRequest dto) {
        if (dto.name() != null) this.name = dto.name();
        if (dto.energy() != null) this.energy = dto.energy();
        if (dto.carb() != null) this.carb = dto.carb();
        if (dto.protein() != null) this.protein = dto.protein();
        if (dto.fat() != null) this.fat = dto.fat();
        if (dto.weight() != null) this.weight = dto.weight();
    }
}
