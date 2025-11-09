package com.babsim.babsimbackend.domain.health.dto.request;

import com.babsim.babsimbackend.domain.health.enums.HealthConditionType;
import io.swagger.v3.oas.annotations.media.Schema;

public record HealthConditionUpdateRequest(
    @Schema(description = "건강 상태 이름", example = "고혈압")
    String name,
    @Schema(description = "건강 상태 타입", example = "HYPERTENSION")
    HealthConditionType type
) {
}
