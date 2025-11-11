package com.babsim.babsimbackend.domain.diet.controller;

import com.babsim.babsimbackend.domain.diet.dto.request.MealCreateRequest;
import com.babsim.babsimbackend.domain.diet.dto.request.MealUpdateRequest;
import com.babsim.babsimbackend.domain.diet.dto.response.MealResponse;
import com.babsim.babsimbackend.domain.diet.service.MealFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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

    private final MealFacade mealFacade;

    @Operation(summary = "식단 정보 생성", description = "새로운 식단 정보를 시스템에 등록합니다.")
    @PostMapping
    public ResponseEntity<Void> createMeal(@Valid @RequestBody MealCreateRequest request) {
        // AI-Refactor: 현재는 userId를 하드코딩하지만, 향후 Spring Security Context에서 가져와야 함
        String userId = "c2a8c3e3-5e6f-4b8a-8f4a-9e4e8c6f2b3a"; // 임시 UUID
        Long mealId = mealFacade.createMeal(request, userId);
        return ResponseEntity.created(URI.create("/api/v1/meals/" + mealId)).build();
    }

    @Operation(summary = "사용자 식단 목록 조회", description = "특정 사용자의 모든 식단 정보를 조회합니다.")
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<MealResponse>> getMealsByUserId(@PathVariable UUID userId) {
        List<MealResponse> responses = mealFacade.findMealsByUserId(userId);
        return ResponseEntity.ok(responses);
    }

    @Operation(summary = "식단 정보 수정", description = "특정 ID의 식단 정보를 수정합니다.")
    @PutMapping("/{mealId}")
    public ResponseEntity<MealResponse> updateMeal(@PathVariable Long mealId, @RequestBody MealUpdateRequest request) {
        MealResponse response = mealFacade.updateMeal(mealId, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "식단 정보 삭제", description = "특정 ID의 식단 정보를 삭제합니다.")
    @DeleteMapping("/{mealId}")
    public ResponseEntity<Void> deleteMeal(@PathVariable Long mealId) {
        mealFacade.deleteMeal(mealId);
        return ResponseEntity.noContent().build();
    }
}
