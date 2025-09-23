package com.babsim.babsimbackend.domain.auth.exception;

import com.babsim.babsimbackend.common.exception.BusinessException;
import com.babsim.babsimbackend.common.exception.ErrorCode;

// AI 생성: 사용자를 찾을 수 없을 때 발생하는 예외
public class UserNotFoundException extends BusinessException {

    public UserNotFoundException() {
        super(ErrorCode.USER_NOT_FOUND);
    }
}
