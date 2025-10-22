package com.babsim.babsimbackend.domain.health.dto.response;

import com.babsim.babsimbackend.domain.health.entity.HealthCondition;
import com.babsim.babsimbackend.domain.health.entity.UserHealthCondition;
import java.util.UUID;

public record UserHealthConditionResponse(
        Long id,
        UUID userId,
        Long healthConditionId,
        String healthConditionName,
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
