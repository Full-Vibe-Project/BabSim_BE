package com.babsim.babsimbackend.domain.health.dto.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record UserHealthConditionUpdateRequest(
        @NotNull(message = "사용자 ID는 필수입니다.")
        UUID userId,
        @NotNull(message = "건강 상태 ID는 필수입니다.")
        Long healthConditionId
) {
}
