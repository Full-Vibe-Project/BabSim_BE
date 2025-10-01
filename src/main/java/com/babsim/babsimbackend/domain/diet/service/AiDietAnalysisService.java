package com.babsim.babsimbackend.domain.diet.service;

import com.babsim.babsimbackend.domain.diet.dto.request.AiDietAnalysisRequest;
import com.babsim.babsimbackend.domain.diet.dto.response.DietEntryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// AI 생성: AI 식단 분석 서비스
@Service
@RequiredArgsConstructor
public class AiDietAnalysisService {

    // private final AiServiceClient aiServiceClient;

    public DietEntryResponse analyzeDietImage(AiDietAnalysisRequest request) {
        // TODO: AI 서비스와 연동하여 이미지 분석 및 영양 정보 추출 로직 구현
        return null;
    }
}
