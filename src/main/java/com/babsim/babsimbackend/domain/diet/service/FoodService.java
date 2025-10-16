package com.babsim.babsimbackend.domain.diet.service;

import com.babsim.babsimbackend.domain.diet.dto.request.FoodCreateRequest;
import com.babsim.babsimbackend.domain.diet.dto.response.FoodResponse;
import com.babsim.babsimbackend.domain.diet.dto.request.FoodUpdateRequest;
import com.babsim.babsimbackend.domain.diet.entity.Food;
import com.babsim.babsimbackend.domain.diet.exception.FoodNotFoundException;
import com.babsim.babsimbackend.domain.diet.repository.FoodRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

// AI 생성: Food 엔티티 관련 비즈니스 로직을 처리하는 서비스 클래스
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FoodService {

    private final FoodRepository foodRepository;

    @Transactional
    public FoodResponse createFood(FoodCreateRequest requestDto) {
        Food newFood = requestDto.toEntity();
        Food savedFood = foodRepository.save(newFood);
        return new FoodResponse(savedFood);
    }

    public FoodResponse findFoodByCode(String code) {
        Food food = foodRepository.findById(code)
            .orElseThrow(() -> new FoodNotFoundException(code));
        return new FoodResponse(food);
    }

    @Transactional
    public FoodResponse updateFood(String code, FoodUpdateRequest requestDto) {
        Food food = foodRepository.findById(code)
            .orElseThrow(() -> new FoodNotFoundException(code));
        food.update(requestDto);
        return new FoodResponse(food);
    }

    @Transactional
    public void deleteFood(String code) {
        foodRepository.deleteById(code);
    }
}
