package com.babsim.babsimbackend.infrastructure.external;

import com.babsim.babsimbackend.domain.diet.entity.Food;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// AI 생성: 음식 DB 외부 API 서비스
@Service
@RequiredArgsConstructor
public class FoodDatabaseService {

    // private final ExternalApiProperties externalApiProperties;

    public Food getFoodInfo(String foodName) {
        // TODO: 외부 음식 영양성분 DB API를 호출하여 음식 정보를 가져오는 로직 구현
        System.out.println("Fetching food info from external DB: " + foodName);
        return null;
    }
}
