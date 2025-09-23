package com.babsim.babsimbackend.domain.health.dto.response;

import com.babsim.babsimbackend.domain.health.entity.HealthMetric;
import lombok.Builder;
import lombok.Getter;

// AI 생성: 건강 지표 응답 DTO
@Getter
public class HealthMetricResponse {
    private final double weight;
    private final double height;
    private final int bloodPressureSystolic;
    private final int bloodPressureDiastolic;
    private final int bloodSugar;

    @Builder
    public HealthMetricResponse(double weight, double height, int bloodPressureSystolic, int bloodPressureDiastolic, int bloodSugar) {
        this.weight = weight;
        this.height = height;
        this.bloodPressureSystolic = bloodPressureSystolic;
        this.bloodPressureDiastolic = bloodPressureDiastolic;
        this.bloodSugar = bloodSugar;
    }

    public static HealthMetricResponse from(HealthMetric healthMetric) {
        return HealthMetricResponse.builder()
                .weight(healthMetric.getWeight())
                .height(healthMetric.getHeight())
                .bloodPressureSystolic(healthMetric.getBloodPressureSystolic())
                .bloodPressureDiastolic(healthMetric.getBloodPressureDiastolic())
                .bloodSugar(healthMetric.getBloodSugar())
                .build();
    }
}
