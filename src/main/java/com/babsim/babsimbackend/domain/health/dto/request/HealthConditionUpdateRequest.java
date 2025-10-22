package com.babsim.babsimbackend.domain.health.dto.request;

import com.babsim.babsimbackend.domain.health.enums.HealthConditionType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record HealthConditionUpdateRequest(
        @NotBlank(message = "건강 상태 이름은 필수입니다.")
        String name,
        @NotNull(message = "건강 상태 유형은 필수입니다.")
        HealthConditionType type
) {
}
