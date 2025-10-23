package com.babsim.babsimbackend.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.sql.DataSource;
import java.util.HashMap;

// AI 생성: TimescaleDB 데이터베이스 JPA 설정
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        basePackages = "com.babsim.babsimbackend.timeseries", // TimescaleDB 엔티티 패키지
        entityManagerFactoryRef = "timescaledbEntityManagerFactory",
        transactionManagerRef = "timescaledbTransactionManager"
)
public class TimescaleDBJpaConfig {

    @Bean
    public LocalContainerEntityManagerFactoryBean timescaledbEntityManagerFactory(
            @Qualifier("timescaledbDataSource") DataSource timescaledbDataSource) {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(timescaledbDataSource);
        em.setPackagesToScan("com.babsim.babsimbackend.timeseries"); // TimescaleDB 엔티티 패키지

        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", "update"); // 개발 환경에서만 사용
        properties.put("hibernate.dialect", "org.hibernate.dialect.PostgreSQLDialect"); // TimescaleDB는 PostgreSQL 기반
        em.setJpaPropertyMap(properties);

        return em;
    }

    @Bean
    public PlatformTransactionManager timescaledbTransactionManager(
            @Qualifier("timescaledbEntityManagerFactory") LocalContainerEntityManagerFactoryBean timescaledbEntityManagerFactory) {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(timescaledbEntityManagerFactory.getObject());
        return transactionManager;
    }
}
