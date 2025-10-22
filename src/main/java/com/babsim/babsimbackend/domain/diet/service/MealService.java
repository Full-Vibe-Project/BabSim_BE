package com.babsim.babsimbackend.domain.diet.service;

import com.babsim.babsimbackend.domain.user.entity.User;
import com.babsim.babsimbackend.domain.diet.dto.response.MealResponse;
import com.babsim.babsimbackend.domain.diet.entity.Food;
import com.babsim.babsimbackend.domain.diet.entity.Meal;
import com.babsim.babsimbackend.domain.diet.entity.MealFood;
import com.babsim.babsimbackend.domain.diet.exception.MealNotFoundException;
import com.babsim.babsimbackend.domain.diet.repository.MealRepository;
import com.babsim.babsimbackend.domain.diet.enums.MealType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

// AI 생성: Meal 엔티티 관련 핵심 비즈니스 로직을 처리하는 서비스 클래스
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MealService {

    private final MealRepository mealRepository;

    @Transactional
    public MealResponse createMeal(User user, MealType mealType, String imageUrl, List<Food> foods, List<Integer> quantities) {
        Meal meal = Meal.builder()
            .user(user)
            .mealType(mealType)
            .imageUrl(imageUrl)
            .build();

        List<MealFood> mealFoods = foods.stream()
            .map(food -> MealFood.builder()
                .meal(meal)
                .food(food)
                .quantity(quantities.get(foods.indexOf(food)))
                .build())
            .collect(Collectors.toList());

        meal.getMealFoods().addAll(mealFoods);

        Meal savedMeal = mealRepository.save(meal);
        return new MealResponse(savedMeal);
    }

    public List<MealResponse> findMealsByUser(User user) {
        return mealRepository.findAllByUser(user).stream()
            .map(MealResponse::new)
            .collect(Collectors.toList());
    }

    @Transactional
    public MealResponse updateMeal(Long mealId, MealType mealType, String imageUrl, List<Food> foods, List<Integer> quantities) {
        Meal meal = mealRepository.findById(mealId)
            .orElseThrow(() -> new MealNotFoundException(mealId));

        List<MealFood> mealFoods = foods.stream()
            .map(food -> MealFood.builder()
                .meal(meal)
                .food(food)
                .quantity(quantities.get(foods.indexOf(food)))
                .build())
            .collect(Collectors.toList());

        meal.update(mealType, imageUrl, mealFoods);

        return new MealResponse(meal);
    }

    @Transactional
    public void deleteMeal(Long mealId) {
        if (!mealRepository.existsById(mealId)) {
            throw new MealNotFoundException(mealId);
        }
        mealRepository.deleteById(mealId);
    }
}
