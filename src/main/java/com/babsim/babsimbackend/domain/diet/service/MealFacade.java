package com.babsim.babsimbackend.domain.diet.service;

import com.babsim.babsimbackend.domain.diet.dto.request.MealCreateRequest;
import com.babsim.babsimbackend.domain.diet.dto.request.MealUpdateRequest;
import com.babsim.babsimbackend.domain.diet.dto.response.MealResponse;
import com.babsim.babsimbackend.domain.diet.entity.Food;
import com.babsim.babsimbackend.domain.user.entity.User;
import com.babsim.babsimbackend.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

// AI 생성: 여러 도메인 서비스를 조합하여 식단 관련 복합 로직을 처리하는 퍼사드 클래스
@Service
@RequiredArgsConstructor
public class MealFacade {

    private final UserService userService;
    private final FoodService foodService;
    private final MealService mealService;

    @Transactional
    public Long createMeal(MealCreateRequest request, String userId) {
        User user = userService.findUserById(UUID.fromString(userId));
        return foodService.createMeal(request, user);
    }

    @Transactional(readOnly = true)
    public List<MealResponse> findMealsByUserId(UUID userId) {
        User user = userService.findUserById(userId);
        return mealService.findMealsByUser(user);
    }

    @Transactional
    public MealResponse updateMeal(Long mealId, MealUpdateRequest request) {
        List<Food> foods = request.foods().stream()
            .map(foodItem -> foodService.findFoodEntityByCode(foodItem.foodCode()))
            .collect(Collectors.toList());

        List<Integer> quantities = request.foods().stream()
            .map(MealUpdateRequest.FoodItem::quantity)
            .collect(Collectors.toList());

        return mealService.updateMeal(mealId, request.mealType(), request.imageUrl(), foods, quantities);
    }

    public void deleteMeal(Long mealId) {
        mealService.deleteMeal(mealId);
    }
}
