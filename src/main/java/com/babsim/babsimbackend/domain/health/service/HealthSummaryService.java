package com.babsim.babsimbackend.domain.health.service;

import com.babsim.babsimbackend.domain.health.dto.response.HealthSummaryResponse;
import com.babsim.babsimbackend.domain.health.enums.PeriodType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// AI 생성: 건강 요약 서비스
@Service
@RequiredArgsConstructor
public class HealthSummaryService {

    // private final HealthDataAggregationService healthDataAggregationService;

    public HealthSummaryResponse getHealthSummary(PeriodType period) {
        // TODO: 주간/월간 데이터 요약 로직 구현
        return null;
    }
}
