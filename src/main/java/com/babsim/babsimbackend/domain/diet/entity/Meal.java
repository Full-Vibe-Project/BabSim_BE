package com.babsim.babsimbackend.domain.diet.entity;

import com.babsim.babsimbackend.common.entity.BaseEntity;
import com.babsim.babsimbackend.domain.user.entity.User;
import com.babsim.babsimbackend.domain.diet.enums.MealType;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

// AI 생성: 사용자의 한 끼 식사를 나타내는 Meal 엔티티
@Entity
@Table(name = "meal")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Meal extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private MealType mealType;

    @Column(name = "image_url")
    private String imageUrl;

    @OneToMany(mappedBy = "meal", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MealFood> mealFoods = new ArrayList<>();

    @Builder
    public Meal(User user, MealType mealType, String imageUrl) {
        this.user = user;
        this.mealType = mealType;
        this.imageUrl = imageUrl;
    }

    public void update(MealType mealType, String imageUrl, List<MealFood> mealFoods) {
        this.mealType = mealType;
        this.imageUrl = imageUrl;
        this.mealFoods.clear();
        this.mealFoods.addAll(mealFoods);
    }
}
