package com.babsim.babsimbackend.domain.diet.repository;

import com.babsim.babsimbackend.domain.user.entity.User;
import com.babsim.babsimbackend.domain.diet.entity.Meal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// AI 생성: Meal 엔티티의 데이터 접근을 위한 Repository 인터페이스
public interface MealRepository extends JpaRepository<Meal, Long> {
    List<Meal> findAllByUser(User user);
}
