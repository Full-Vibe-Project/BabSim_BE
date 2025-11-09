package com.babsim.babsimbackend.domain.health.dto.response;

import com.babsim.babsimbackend.domain.health.entity.HealthCondition;
import com.babsim.babsimbackend.domain.health.enums.HealthConditionType;
import io.swagger.v3.oas.annotations.media.Schema;

public record HealthConditionResponse(
    @Schema(description = "건강 상태 ID", example = "1")
    Long id,
    @Schema(description = "건강 상태 이름", example = "당뇨")
    String name,
    @Schema(description = "건강 상태 타입", example = "DIABETES")
    HealthConditionType type
) {
    public static HealthConditionResponse from(HealthCondition healthCondition) {
        return new HealthConditionResponse(
                healthCondition.getId(),
                healthCondition.getName(),
                healthCondition.getType()
        );
    }
}
