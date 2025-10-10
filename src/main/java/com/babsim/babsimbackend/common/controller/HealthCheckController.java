package com.babsim.babsimbackend.common.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

// AI 생성: 애플리케이션의 상태를 확인하기 위한 Health Check 컨트롤러
@Tag(name = "Health Check", description = "애플리케이션 상태 확인 API")
@RestController
public class HealthCheckController {

    @Operation(summary = "Health Check", description = "애플리케이션이 정상적으로 실행 중인지 확인합니다.")
    @GetMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("OK");
    }
}
