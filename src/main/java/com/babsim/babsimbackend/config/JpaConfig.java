package com.babsim.babsimbackend.config;

import com.babsim.babsimbackend.config.properties.DataSourceProperties;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.sql.DataSource;

// AI 생성: JPA Auditing 기능을 활성화하기 위한 설정 클래스 및 다중 데이터소스 설정
@Configuration
@EnableJpaAuditing
@RequiredArgsConstructor
public class JpaConfig {

    private final DataSourceProperties dataSourceProperties;

    @Profile("!test")
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

    @Profile("!test")
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

    @Profile("test")
    @Primary
    @Bean
    public DataSource mysqlTestDataSource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .url("jdbc:h2:mem:mysql_test_db;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE")
                .username("sa")
                .password("")
                .driverClassName("org.h2.Driver")
                .build();
    }

    @Profile("test")
    @Bean
    public DataSource timescaledbTestDataSource() {
        return DataSourceBuilder.create()
                .type(HikariDataSource.class)
                .url("jdbc:h2:mem:timescaledb_test_db;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE")
                .username("sa")
                .password("")
                .driverClassName("org.h2.Driver")
                .build();
    }
}
