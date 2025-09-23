package com.babsim.babsimbackend.common.util;

import java.util.regex.Pattern;

// AI 생성: 검증 유틸리티 클래스
public class ValidationUtils {

    private static final String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    public static boolean isEmail(String email) {
        return Pattern.matches(EMAIL_REGEX, email);
    }
}
