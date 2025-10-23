package com.babsim.babsimbackend.domain.diet.repository;

import com.babsim.babsimbackend.domain.diet.dto.response.DailyNutritionSummaryResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

// AI 생성: 일별 영양소 합계를 조회하는 리포지토리 (MySQL)
@Repository
public interface DailyNutritionSummaryRepository extends JpaRepository<Object, Long> {

    // v_daily_nutrition 뷰 또는 유사한 집계 쿼리를 사용하여 일별 영양 데이터를 조회
    @Query(value = """
            SELECT new com.babsim.babsimbackend.domain.diet.dto.response.DailyNutritionSummaryResponse(
                m.userId, 
                DATE(m.createdAt) AS date,
                SUM(f.energy * mf.quantity / 100) AS totalEnergy,
                SUM(f.protein * mf.quantity / 100) AS totalProtein,
                SUM(f.carb * mf.quantity / 100) AS totalCarb,
                SUM(f.fat * mf.quantity / 100) AS totalFat
            )
            FROM Meal m
            JOIN MealFood mf ON m.id = mf.meal.id
            JOIN Food f ON mf.food.code = f.code
            WHERE DATE(m.createdAt) = :date
            GROUP BY m.userId, DATE(m.createdAt)
            """, nativeQuery = false)
    List<DailyNutritionSummaryResponse> findDailyNutritionSummaryByDate(@Param("date") LocalDate date);
}
