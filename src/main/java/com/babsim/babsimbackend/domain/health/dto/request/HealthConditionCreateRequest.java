package com.babsim.babsimbackend.domain.health.dto.request;

import com.babsim.babsimbackend.domain.health.enums.HealthConditionType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

public record HealthConditionCreateRequest(
    @Schema(description = "건강 상태 이름", example = "당뇨")
    @NotBlank String name,
    @Schema(description = "건강 상태 타입", example = "DIABETES")
    HealthConditionType type
) {
}
