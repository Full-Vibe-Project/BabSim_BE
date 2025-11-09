package com.babsim.babsimbackend.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

// AI 생성: 다중 데이터베이스 관련 설정값
@Getter
@Component
@ConfigurationProperties(prefix = "spring.datasource")
public class DataSourceProperties {

    private final MySQL mysql = new MySQL();
    private final TimescaleDB timescaledb = new TimescaleDB();

    @Getter
    @Setter
    public static class MySQL {
        private String url;
        private String username;
        private String password;
        private String driverClassName;
    }

    @Getter
    @Setter
    public static class TimescaleDB {
        private String url;
        private String username;
        private String password;
        private String driverClassName;
    }
}
