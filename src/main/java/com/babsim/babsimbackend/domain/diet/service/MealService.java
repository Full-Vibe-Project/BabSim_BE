package com.babsim.babsimbackend.domain.diet.service;

import com.babsim.babsimbackend.domain.user.entity.User;
import com.babsim.babsimbackend.domain.user.exception.UserNotFoundException;
import com.babsim.babsimbackend.domain.user.repository.UserRepository;
import com.babsim.babsimbackend.domain.diet.dto.request.MealCreateRequest;
import com.babsim.babsimbackend.domain.diet.dto.request.MealUpdateRequest;
import com.babsim.babsimbackend.domain.diet.dto.response.MealResponse;
import com.babsim.babsimbackend.domain.diet.entity.Food;
import com.babsim.babsimbackend.domain.diet.entity.Meal;
import com.babsim.babsimbackend.domain.diet.entity.MealFood;
import com.babsim.babsimbackend.domain.diet.exception.FoodNotFoundException;
import com.babsim.babsimbackend.domain.diet.exception.MealNotFoundException;
import com.babsim.babsimbackend.domain.diet.repository.FoodRepository;
import com.babsim.babsimbackend.domain.diet.repository.MealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

// AI 생성: Meal 엔티티 관련 비즈니스 로직을 처리하는 서비스 클래스
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MealService {

    private final MealRepository mealRepository;
    private final UserRepository userRepository;
    private final FoodRepository foodRepository;

    @Transactional
    public MealResponse createMeal(MealCreateRequest request) {
        User user = userRepository.findById(request.getUserId())
            .orElseThrow(UserNotFoundException::new);

        Meal meal = Meal.builder()
            .user(user)
            .mealType(request.getMealType())
            .imageUrl(request.getImageUrl())
            .build();

        List<MealFood> mealFoods = request.getFoods().stream()
            .map(foodItem -> {
                Food food = foodRepository.findById(foodItem.getFoodCode())
                    .orElseThrow(() -> new FoodNotFoundException(foodItem.getFoodCode()));
                return MealFood.builder()
                    .meal(meal)
                    .food(food)
                    .quantity(foodItem.getQuantity())
                    .build();
            })
            .collect(Collectors.toList());

        meal.getMealFoods().addAll(mealFoods);

        Meal savedMeal = mealRepository.save(meal);
        return new MealResponse(savedMeal);
    }

    public List<MealResponse> findMealsByUserId(UUID userId) {
        User user = userRepository.findById(userId)
            .orElseThrow(UserNotFoundException::new);

        return mealRepository.findAllByUser(user).stream()
            .map(MealResponse::new)
            .collect(Collectors.toList());
    }

    @Transactional
    public MealResponse updateMeal(Long mealId, MealUpdateRequest request) {
        Meal meal = mealRepository.findById(mealId)
            .orElseThrow(() -> new MealNotFoundException(mealId));

        List<MealFood> mealFoods = request.getFoods().stream()
            .map(foodItem -> {
                Food food = foodRepository.findById(foodItem.getFoodCode())
                    .orElseThrow(() -> new FoodNotFoundException(foodItem.getFoodCode()));
                return MealFood.builder()
                    .meal(meal)
                    .food(food)
                    .quantity(foodItem.getQuantity())
                    .build();
            })
            .collect(Collectors.toList());

        meal.update(request.getMealType(), request.getImageUrl(), mealFoods);

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
