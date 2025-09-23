package com.babsim.babsimbackend.domain.diet.controller;

import com.babsim.babsimbackend.common.dto.BaseResponse;
import com.babsim.babsimbackend.domain.diet.dto.request.DietEntryRequest;
import com.babsim.babsimbackend.domain.diet.dto.response.DailyDietResponse;
import com.babsim.babsimbackend.domain.diet.service.DietService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

// AI 생성: 식단 기록 API 컨트롤러
@RestController
@RequestMapping("/api/v1/diet")
@RequiredArgsConstructor
public class DietController {

    private final DietService dietService;

    @PostMapping("/entry")
    public ResponseEntity<BaseResponse<Void>> createDietEntry(@RequestBody DietEntryRequest request) {
        // TODO: 수동 식단 기록 로직 구현
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/daily/{date}")
    public ResponseEntity<BaseResponse<DailyDietResponse>> getDailyDiet(
            @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        // TODO: 일일 식단 조회 로직 구현
        return ResponseEntity.ok(null);
    }
}
