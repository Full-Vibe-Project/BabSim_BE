package com.babsim.babsimbackend.domain.diet.repository;

import com.babsim.babsimbackend.domain.diet.entity.MealFood;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MealFoodRepository extends JpaRepository<MealFood, Long> {
}
