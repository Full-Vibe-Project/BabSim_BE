package com.babsim.babsimbackend.domain.health.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

// AI 생성: 위험 분석 응답 DTO
@Getter
public class RiskAnalysisResponse {
    private final List<RiskInfo> risks;

    @Builder
    public RiskAnalysisResponse(List<RiskInfo> risks) {
        this.risks = risks;
    }

    @Getter
    public static class RiskInfo {
        private final String riskName;
        private final RiskLevel riskLevel;
        private final String description;

        @Builder
        public RiskInfo(String riskName, RiskLevel riskLevel, String description) {
            this.riskName = riskName;
            this.riskLevel = riskLevel;
            this.description = description;
        }
    }
}
