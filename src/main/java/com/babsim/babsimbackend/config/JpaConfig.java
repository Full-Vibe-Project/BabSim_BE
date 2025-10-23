package com.babsim.babsimbackend.config;

import com.babsim.babsimbackend.config.properties.DataSourceProperties;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.sql.DataSource;

// AI 생성: JPA Auditing 기능을 활성화하기 위한 설정 클래스 및 다중 데이터소스 설정
@Configuration
@EnableJpaAuditing
@RequiredArgsConstructor
public class JpaConfig {

    private final DataSourceProperties dataSourceProperties;

    @Primary
    @Bean
    public DataSource mysqlDataSource() {
        DataSourceProperties.MySQL mysql = dataSourceProperties.getMysql();
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .url(mysql.getUrl())
                .username(mysql.getUsername())
                .password(mysql.getPassword())
                .driverClassName(mysql.getDriverClassName())
                .build();
    }

    @Bean
    public DataSource timescaledbDataSource() {
        DataSourceProperties.TimescaleDB timescaledb = dataSourceProperties.getTimescaledb();
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .url(timescaledb.getUrl())
                .username(timescaledb.getUsername())
                .password(timescaledb.getPassword())
                .driverClassName(timescaledb.getDriverClassName())
                .build();
    }
}
