package com.babsim.babsimbackend.domain.diet.service;

import com.babsim.babsimbackend.domain.diet.dto.request.DietEntryRequest;
import com.babsim.babsimbackend.domain.diet.dto.response.DailyDietResponse;
import com.babsim.babsimbackend.domain.diet.repository.DietEntryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

// AI 생성: 식단 기록 비즈니스 로직 서비스
@Service
@RequiredArgsConstructor
public class DietService {

    private final DietEntryRepository dietEntryRepository;

    public void createDietEntry(DietEntryRequest request) {
        // TODO: 수동 식단 기록 로직 구현
    }

    public DailyDietResponse getDailyDiet(LocalDate date) {
        // TODO: 일일 식단 조회 로직 구현
        return null;
    }
}
