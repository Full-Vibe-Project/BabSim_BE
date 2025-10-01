package com.babsim.babsimbackend.domain.diet.controller;

import com.babsim.babsimbackend.common.dto.BaseResponse;
import com.babsim.babsimbackend.domain.diet.dto.request.AiDietAnalysisRequest;
import com.babsim.babsimbackend.domain.diet.dto.response.DietEntryResponse;
import com.babsim.babsimbackend.domain.diet.service.AiDietAnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// AI 생성: AI 기반 식단 분석 API 컨트롤러
@RestController
@RequestMapping("/api/v1/diet/ai")
@RequiredArgsConstructor
public class AiDietController {

    private final AiDietAnalysisService aiDietAnalysisService;

    @PostMapping("/entry")
    public ResponseEntity<BaseResponse<DietEntryResponse>> analyzeDietImage(@RequestBody AiDietAnalysisRequest request) {
        // TODO: AI 기반 식단 분석 로직 구현
        return ResponseEntity.ok(null);
    }
}
