package com.babsim.babsimbackend.domain.user.exception;

import com.babsim.babsimbackend.common.exception.BusinessException;
import com.babsim.babsimbackend.common.exception.ErrorCode;

// AI 생성: 인증 실패 시 발생하는 예외
public class AuthenticationException extends BusinessException {

    public AuthenticationException() {
        super(ErrorCode.LOGIN_INPUT_INVALID);
    }
}
