package com.babsim.babsimbackend.domain.health.exception;

import com.babsim.babsimbackend.common.exception.BusinessException;
import com.babsim.babsimbackend.common.exception.ErrorCode;

public class HealthConditionNotFoundException extends BusinessException {
    public HealthConditionNotFoundException() {
        super(ErrorCode.HEALTH_CONDITION_NOT_FOUND);
    }
}
