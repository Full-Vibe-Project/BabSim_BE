package com.babsim.babsimbackend.infrastructure.vision;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@Profile("!prod") // prod 프로필이 아닐 때만 활성화
public class MockFoodVisionClient implements FoodVisionClient {

    @Override
    public List<String> analyzeImage(String imageUrl) {
        // AI-Refactor: 실제 Google Vision API를 호출하는 대신, 고정된 음식 이름 리스트를 반환합니다.
        return Arrays.asList("김치찌개", "쌀밥", "계란말이");
    }
}
