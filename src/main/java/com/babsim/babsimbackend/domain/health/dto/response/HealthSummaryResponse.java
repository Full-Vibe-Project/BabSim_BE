package com.babsim.babsimbackend.domain.health.dto.response;

import lombok.Builder;
import lombok.Getter;

// AI 생성: 건강 요약 응답 DTO
@Getter
public class HealthSummaryResponse {
    private final String period;
    private final double averageCalories;
    private final String aiSummary;

    @Builder
    public HealthSummaryResponse(String period, double averageCalories, String aiSummary) {
        this.period = period;
        this.averageCalories = averageCalories;
        this.aiSummary = aiSummary;
    }
}
