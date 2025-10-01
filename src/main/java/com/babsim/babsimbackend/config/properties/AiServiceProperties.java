package com.babsim.babsimbackend.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

// AI 생성: AI 서비스 관련 설정값
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "ai.service")
public class AiServiceProperties {
    private String apiKey;
    private String apiUrl;
}
