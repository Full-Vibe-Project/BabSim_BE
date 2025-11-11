package com.babsim.babsimbackend.domain.diet.service;

import com.babsim.babsimbackend.domain.diet.dto.request.FoodAnalyzeRequest;
import com.babsim.babsimbackend.domain.diet.dto.response.FoodAnalyzeResponse;
import com.babsim.babsimbackend.domain.diet.dto.response.FoodImageUploadResponse;
import com.babsim.babsimbackend.domain.diet.dto.request.MealCreateRequest;
import com.babsim.babsimbackend.domain.diet.dto.request.FoodCreateRequest;
import com.babsim.babsimbackend.domain.diet.dto.response.FoodResponse;
import com.babsim.babsimbackend.domain.diet.dto.request.FoodUpdateRequest;
import com.babsim.babsimbackend.domain.diet.entity.Food;
import com.babsim.babsimbackend.domain.diet.entity.Meal;
import com.babsim.babsimbackend.domain.diet.entity.MealFood;
import com.babsim.babsimbackend.domain.diet.exception.FoodNotFoundException;
import com.babsim.babsimbackend.domain.diet.repository.FoodRepository;
import com.babsim.babsimbackend.domain.diet.repository.MealFoodRepository;
import com.babsim.babsimbackend.domain.diet.repository.MealRepository;
import com.babsim.babsimbackend.domain.user.entity.User;
import com.babsim.babsimbackend.infrastructure.s3.S3Uploader;
import com.babsim.babsimbackend.infrastructure.vision.FoodVisionClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

// AI 생성: Food 엔티티 관련 비즈니스 로직을 처리하는 서비스 클래스
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FoodService {

    private final FoodRepository foodRepository;
    private final MealRepository mealRepository;
    private final MealFoodRepository mealFoodRepository;
    private final S3Uploader s3Uploader;
    private final FoodVisionClient foodVisionClient;

    private static final String S3_DIR_NAME = "food-images";

    @Transactional
    public FoodImageUploadResponse uploadFoodImage(MultipartFile foodImage) throws IOException {
        String imageUrl = s3Uploader.upload(foodImage, S3_DIR_NAME);
        return new FoodImageUploadResponse(imageUrl);
    }

    public FoodAnalyzeResponse analyzeFoodImage(FoodAnalyzeRequest request) {
        List<String> recognizedFoodNames = foodVisionClient.analyzeImage(request.imageUrl());

        List<FoodAnalyzeResponse.AnalyzedFood> analyzedFoods = recognizedFoodNames.stream()
            .map(this::findBestMatchingFood)
            .collect(Collectors.toList());

        return new FoodAnalyzeResponse(analyzedFoods);
    }

    private FoodAnalyzeResponse.AnalyzedFood findBestMatchingFood(String recognizedName) {
        // AI-Refactor: 현재는 이름으로만 검색하지만, 향후 벡터 검색 등으로 고도화 필요
        Food matchedFood = foodRepository.findByName(recognizedName).orElse(null);
        return new FoodAnalyzeResponse.AnalyzedFood(recognizedName, matchedFood);
    }

    @Transactional
    public Long createMeal(MealCreateRequest request, User user) {
        Meal meal = Meal.builder()
            .user(user)
            .mealType(request.mealType())
            .imageUrl(request.imageUrl())
            .build();
        Meal savedMeal = mealRepository.save(meal);

        List<MealFood> mealFoods = request.foods().stream()
            .map(selectedFood -> {
                Food food = findFoodEntityByCode(selectedFood.foodCode());
                return MealFood.builder()
                    .meal(savedMeal)
                    .food(food)
                    .quantity(selectedFood.quantity())
                    .build();
            })
            .collect(Collectors.toList());

        mealFoodRepository.saveAll(mealFoods);
        return savedMeal.getId();
    }

    @Transactional
    public FoodResponse createFood(FoodCreateRequest requestDto) {
        Food newFood = requestDto.toEntity();
        Food savedFood = foodRepository.save(newFood);
        return new FoodResponse(savedFood);
    }

    public FoodResponse findFoodByCode(String code) {
        return new FoodResponse(findFoodEntityByCode(code));
    }

    public Food findFoodEntityByCode(String code) {
        return foodRepository.findById(code)
            .orElseThrow(() -> new FoodNotFoundException(code));
    }

    @Transactional
    public FoodResponse updateFood(String code, FoodUpdateRequest requestDto) {
        Food food = findFoodEntityByCode(code);
        food.update(requestDto);
        return new FoodResponse(food);
    }

    @Transactional
    public void deleteFood(String code) {
        foodRepository.deleteById(code);
    }
}
