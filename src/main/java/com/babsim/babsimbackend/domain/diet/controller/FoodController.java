package com.babsim.babsimbackend.domain.diet.controller;

import com.babsim.babsimbackend.domain.diet.dto.request.FoodCreateRequest;
import com.babsim.babsimbackend.domain.diet.dto.response.FoodResponse;
import com.babsim.babsimbackend.domain.diet.dto.request.FoodUpdateRequest;
import com.babsim.babsimbackend.domain.diet.service.FoodService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

// AI 생성: Food 관련 API 요청을 처리하는 컨트롤러
@Tag(name = "Food", description = "음식 정보 API")
@RestController
@RequestMapping("/api/v1/foods")
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;

    @Operation(summary = "음식 정보 생성", description = "새로운 음식 정보를 시스템에 등록합니다.")
    @PostMapping
    public ResponseEntity<FoodResponse> createFood(@RequestBody FoodCreateRequest requestDto) {
        FoodResponse responseDto = foodService.createFood(requestDto);
        return ResponseEntity.created(URI.create("/api/v1/foods/" + responseDto.getCode()))
            .body(responseDto);
    }

    @Operation(summary = "음식 정보 조회", description = "특정 코드에 해당하는 음식 정보를 조회합니다.")
    @GetMapping("/{code}")
    public ResponseEntity<FoodResponse> getFoodByCode(@PathVariable String code) {
        FoodResponse responseDto = foodService.findFoodByCode(code);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "음식 정보 수정", description = "특정 코드에 해당하는 음식 정보를 수정합니다.")
    @PutMapping("/{code}")
    public ResponseEntity<FoodResponse> updateFood(@PathVariable String code, @RequestBody FoodUpdateRequest requestDto) {
        FoodResponse responseDto = foodService.updateFood(code, requestDto);
        return ResponseEntity.ok(responseDto);
    }

    @Operation(summary = "음식 정보 삭제", description = "특정 코드에 해당하는 음식 정보를 삭제합니다.")
    @DeleteMapping("/{code}")
    public ResponseEntity<Void> deleteFood(@PathVariable String code) {
        foodService.deleteFood(code);
        return ResponseEntity.noContent().build();
    }
}
