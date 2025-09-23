package com.babsim.babsimbackend.infrastructure.external;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// AI 생성: Notion MCP 통합 서비스
@Service
@RequiredArgsConstructor
public class NotionIntegrationService {

    // private final ExternalApiProperties externalApiProperties;

    public void syncToNotion(String content) {
        // TODO: Notion API를 호출하여 데이터를 동기화하는 로직 구현
        System.out.println("Syncing to Notion: " + content);
    }
}
