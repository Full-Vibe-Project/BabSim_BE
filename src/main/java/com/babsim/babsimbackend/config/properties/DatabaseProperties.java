package com.babsim.babsimbackend.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

// AI 생성: 데이터베이스 관련 설정값
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "spring.datasource")
public class DatabaseProperties {
    private String url;
    private String username;
    private String password;
    private String driverClassName;
}
