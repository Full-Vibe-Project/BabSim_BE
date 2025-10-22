package com.babsim.babsimbackend.domain.health.dto.response;

import com.babsim.babsimbackend.domain.health.entity.HealthCondition;
import com.babsim.babsimbackend.domain.health.enums.HealthConditionType;

public record HealthConditionResponse(
        Long id,
        String name,
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
