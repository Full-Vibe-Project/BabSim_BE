package com.babsim.babsimbackend.domain.health.controller;

import com.babsim.babsimbackend.common.dto.BaseResponse;
import com.babsim.babsimbackend.domain.health.dto.response.RiskAnalysisResponse;
import com.babsim.babsimbackend.domain.health.service.RiskAnalysisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// AI 생성: 건강 위험 분석 API 컨트롤러
@RestController
@RequestMapping("/api/v1/health")
@RequiredArgsConstructor
public class RiskAnalysisController {

    private final RiskAnalysisService riskAnalysisService;

    @GetMapping("/risk-analysis")
    public ResponseEntity<BaseResponse<RiskAnalysisResponse>> getRiskAnalysis() {
        // TODO: AI 기반 건강 위험 분석 로직 구현
        return ResponseEntity.ok(null);
    }
}
