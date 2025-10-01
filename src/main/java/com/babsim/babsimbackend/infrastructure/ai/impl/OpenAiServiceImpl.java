package com.babsim.babsimbackend.infrastructure.ai.impl;

import com.babsim.babsimbackend.domain.diet.dto.response.DietEntryResponse;
import com.babsim.babsimbackend.infrastructure.ai.AiServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// AI 생성: OpenAI 구현체 (대안)
@Service
@RequiredArgsConstructor
public class OpenAiServiceImpl implements AiServiceClient {

    // private final AiServiceProperties aiServiceProperties;

    @Override
    public DietEntryResponse analyzeImage(String imageUrl) {
        // TODO: OpenAI API를 호출하여 이미지 분석 로직 구현
        System.out.println("Analyzing image with OpenAI: " + imageUrl);
        return null;
    }
}
