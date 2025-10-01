package com.babsim.babsimbackend.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

// AI 생성: 외부 API 관련 설정값
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "external.api")
public class ExternalApiProperties {
    private String notionApiUrl;
    private String foodDbApiUrl;
    private String healthApiUrl;
}
