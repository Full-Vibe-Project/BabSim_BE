package com.babsim.babsimbackend.domain.recommendation.controller;

import com.babsim.babsimbackend.common.dto.BaseResponse;
import com.babsim.babsimbackend.domain.recommendation.dto.response.MenuRecommendationResponse;
import com.babsim.babsimbackend.domain.recommendation.service.MenuRecommendationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// AI 생성: 메뉴 추천 API 컨트롤러
@RestController
@RequestMapping("/api/v1/recommendation")
@RequiredArgsConstructor
public class RecommendationController {

    private final MenuRecommendationService menuRecommendationService;

    @GetMapping("/menu")
    public ResponseEntity<BaseResponse<MenuRecommendationResponse>> getMenuRecommendation() {
        // TODO: 맞춤 메뉴 추천 로직 구현
        return ResponseEntity.ok(null);
    }
}
