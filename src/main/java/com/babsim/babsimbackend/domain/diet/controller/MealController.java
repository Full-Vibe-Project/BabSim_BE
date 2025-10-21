package com.babsim.babsimbackend.domain.diet.controller;

import com.babsim.babsimbackend.domain.diet.dto.request.MealCreateRequest;
import com.babsim.babsimbackend.domain.diet.dto.request.MealUpdateRequest;
import com.babsim.babsimbackend.domain.diet.dto.response.MealResponse;
import com.babsim.babsimbackend.domain.diet.service.MealService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.UUID;

// AI 생성: Meal 관련 API 요청을 처리하는 컨트롤러
@Tag(name = "Meal", description = "식단 정보 API")
@RestController
@RequestMapping("/api/v1/meals")
@RequiredArgsConstructor
public class MealController {

    private final MealService mealService;

    @Operation(summary = "식단 정보 생성", description = "새로운 식단 정보를 시스템에 등록합니다.")
    @PostMapping
    public ResponseEntity<MealResponse> createMeal(@RequestBody MealCreateRequest request) {
        MealResponse response = mealService.createMeal(request);
        return ResponseEntity.created(URI.create("/api/v1/meals/" + response.getId()))
            .body(response);
    }

    @Operation(summary = "사용자 식단 목록 조회", description = "특정 사용자의 모든 식단 정보를 조회합니다.")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<MealResponse>> getMealsByUserId(@PathVariable UUID userId) {
        List<MealResponse> responses = mealService.findMealsByUserId(userId);
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "식단 정보 수정", description = "특정 ID의 식단 정보를 수정합니다.")
    @PutMapping("/{mealId}")
    public ResponseEntity<MealResponse> updateMeal(@PathVariable Long mealId, @RequestBody MealUpdateRequest request) {
        MealResponse response = mealService.updateMeal(mealId, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "식단 정보 삭제", description = "특정 ID의 식단 정보를 삭제합니다.")
    @DeleteMapping("/{mealId}")
    public ResponseEntity<Void> deleteMeal(@PathVariable Long mealId) {
        mealService.deleteMeal(mealId);
        return ResponseEntity.noContent().build();
    }
}
