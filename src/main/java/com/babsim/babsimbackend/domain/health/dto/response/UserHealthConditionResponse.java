package com.babsim.babsimbackend.domain.health.dto.response;

import com.babsim.babsimbackend.domain.health.entity.HealthCondition;
import com.babsim.babsimbackend.domain.health.entity.UserHealthCondition;
import com.babsim.babsimbackend.domain.health.entity.UserHealthCondition;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.UUID;

public record UserHealthConditionResponse(
    @Schema(description = "사용자 건강 상태 ID", example = "10")
    Long id,
    @Schema(description = "사용자 ID", example = "a1b2c3d4-e5f6-7890-1234-567890abcdef")
    UUID userId,
    @Schema(description = "건강 상태 ID", example = "1")
    Long healthConditionId,
    @Schema(description = "건강 상태 이름", example = "당뇨")
    String healthConditionName,
    @Schema(description = "건강 상태 타입", example = "DIABETES")
    String healthConditionType
) {
    public static UserHealthConditionResponse from(UserHealthCondition userHealthCondition) {
        return new UserHealthConditionResponse(
                userHealthCondition.getId(),
                userHealthCondition.getUser().getId(),
                userHealthCondition.getHealthCondition().getId(),
                userHealthCondition.getHealthCondition().getName(),
                userHealthCondition.getHealthCondition().getType().name()
        );
    }
}
