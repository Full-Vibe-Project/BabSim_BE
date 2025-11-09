package com.babsim.babsimbackend.domain.health.dto.request;

import jakarta.validation.constraints.NotNull;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

public record UserHealthConditionCreateRequest(
    @Schema(description = "사용자 ID", example = "a1b2c3d4-e5f6-7890-1234-567890abcdef")
    UUID userId,
    @Schema(description = "건강 상태 ID", example = "1")
    Long healthConditionId
) {
}
