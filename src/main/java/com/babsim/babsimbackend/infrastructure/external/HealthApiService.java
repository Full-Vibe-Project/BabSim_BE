package com.babsim.babsimbackend.infrastructure.external;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// AI 생성: 건강검진 API 서비스
@Service
@RequiredArgsConstructor
public class HealthApiService {

    // private final ExternalApiProperties externalApiProperties;

    public void getHealthCheckupData() {
        // TODO: 외부 건강검진 API를 호출하여 사용자 건강 데이터를 가져오는 로직 구현
        System.out.println("Fetching health checkup data from external API");
    }
}
