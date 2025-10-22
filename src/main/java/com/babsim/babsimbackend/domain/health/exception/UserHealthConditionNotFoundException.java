package com.babsim.babsimbackend.domain.health.exception;

import com.babsim.babsimbackend.common.exception.BusinessException;
import com.babsim.babsimbackend.common.exception.ErrorCode;

public class UserHealthConditionNotFoundException extends BusinessException {
    public UserHealthConditionNotFoundException() {
        super(ErrorCode.USER_HEALTH_CONDITION_NOT_FOUND);
    }
}
