package com.babsim.babsimbackend.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. CSRF(Cross-Site Request Forgery) 비활성화 (Stateless API)
            .csrf(csrf -> csrf.disable())

            // 2. 세션 관리 정책을 STATELESS로 설정 (JWT 사용)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // 3. HTTP 요청에 대한 인가 규칙 설정
            .authorizeHttpRequests(auth -> auth
                // Swagger, API 문서, 모니터링 엔드포인트는 인증 없이 허용
                .requestMatchers(
                    "/swagger-ui.html",
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                    "/api-docs/**",
                    "/management/**" 
                ).permitAll()
                // 사용자 인증(회원가입/로그인) API는 허용
                .requestMatchers("/auth/**").permitAll()
                // 그 외 모든 요청은 인증 필요
                .anyRequest().authenticated()
            );
            
            // TODO: JWT 필터 추가 (JwtAuthenticationFilter를 UsernamePasswordAuthenticationFilter 앞에 추가)

        return http.build();
    }
}
