package com.babsim.babsimbackend.domain.health.dto.request;

import com.babsim.babsimbackend.domain.health.enums.PeriodType;
import lombok.Getter;
import lombok.NoArgsConstructor;

// AI 생성: 건강 요약 요청 DTO
@Getter
@NoArgsConstructor
public class HealthSummaryRequest {
    private PeriodType period;
}
