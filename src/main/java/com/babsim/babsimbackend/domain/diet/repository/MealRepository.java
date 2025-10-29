package com.babsim.babsimbackend.domain.diet.repository;

import com.babsim.babsimbackend.domain.user.entity.User;
import com.babsim.babsimbackend.domain.diet.entity.Meal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// AI 생성: Meal 엔티티의 데이터 접근을 위한 Repository 인터페이스
import com.babsim.babsimbackend.domain.diet.dto.response.DailyNutritionSumDto;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.UUID;

// AI 생성: Meal 엔티티의 데이터 접근을 위한 Repository 인터페이스
public interface MealRepository extends JpaRepository<Meal, Long> {
    List<Meal> findAllByUser(User user);

    @Query("SELECT new com.babsim.babsimbackend.domain.diet.dto.response.DailyNutritionSumDto(" +
           "SUM(f.energy * mf.quantity), " +
           "SUM(f.protein * mf.quantity), " +
           "SUM(f.carb * mf.quantity), " +
           "SUM(f.fat * mf.quantity)) " +
           "FROM Meal m " +
           "JOIN m.mealFoods mf " +
           "JOIN mf.food f " +
           "WHERE m.user.id = :userId AND m.createdAt >= :startOfDay AND m.createdAt < :endOfDay")
    DailyNutritionSumDto findDailyNutritionSumByUserIdAndDate(@Param("userId") UUID userId, @Param("startOfDay") LocalDateTime startOfDay, @Param("endOfDay") LocalDateTime endOfDay);
}
