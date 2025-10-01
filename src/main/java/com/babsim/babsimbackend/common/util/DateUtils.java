package com.babsim.babsimbackend.common.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

// AI 생성: 날짜 유틸리티 클래스
public class DateUtils {

    public static String toString(LocalDateTime localDateTime) {
        return localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}
