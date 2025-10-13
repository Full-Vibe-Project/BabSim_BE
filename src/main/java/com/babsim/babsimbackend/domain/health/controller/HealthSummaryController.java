package com.babsim.babsimbackend.domain.health.controller;

import com.babsim.babsimbackend.common.dto.BaseResponse;
import com.babsim.babsimbackend.domain.health.dto.response.HealthSummaryResponse;
import com.babsim.babsimbackend.domain.health.service.HealthSummaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

// AI 생성: 건강 요약 API 컨트롤러
@RestController
@RequestMapping("/api/v1/health")
@RequiredArgsConstructor
public class HealthSummaryController {

    private final HealthSummaryService healthSummaryService;

    @GetMapping("/summary/{period}")
    public ResponseEntity<BaseResponse<HealthSummaryResponse>> getHealthSummary(@PathVariable PeriodType period) {
        // TODO: 주간/월간 데이터 요약 로직 구현
        return ResponseEntity.ok(null);
    }
}
