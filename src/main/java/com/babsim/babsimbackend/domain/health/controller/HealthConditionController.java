package com.babsim.babsimbackend.domain.health.controller;

import com.babsim.babsimbackend.common.dto.BaseResponse;
import com.babsim.babsimbackend.domain.health.dto.request.HealthConditionCreateRequest;
import com.babsim.babsimbackend.domain.health.dto.request.HealthConditionUpdateRequest;
import com.babsim.babsimbackend.domain.health.dto.response.HealthConditionResponse;
import com.babsim.babsimbackend.domain.health.service.HealthConditionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Health Condition", description = "건강 상태 관련 API")
@RestController
@RequestMapping("/api/v1/health-conditions")
@RequiredArgsConstructor
public class HealthConditionController {

    private final HealthConditionService healthConditionService;

    @Operation(summary = "건강 상태 생성", description = "새로운 건강 상태를 생성합니다.")
    @PostMapping
    public ResponseEntity<BaseResponse<HealthConditionResponse>> createHealthCondition(@Valid @RequestBody HealthConditionCreateRequest request) {
        HealthConditionResponse response = HealthConditionResponse.from(healthConditionService.createHealthCondition(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.success(response));
    }

    @Operation(summary = "건강 상태 단일 조회", description = "ID를 통해 특정 건강 상태를 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<HealthConditionResponse>> getHealthConditionById(@PathVariable Long id) {
        HealthConditionResponse response = HealthConditionResponse.from(healthConditionService.getHealthConditionById(id));
        return ResponseEntity.ok(BaseResponse.success(response));
    }

    @Operation(summary = "모든 건강 상태 조회", description = "모든 건강 상태 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<BaseResponse<List<HealthConditionResponse>>> getAllHealthConditions() {
        List<HealthConditionResponse> responses = healthConditionService.getAllHealthConditions().stream()
                .map(HealthConditionResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(BaseResponse.success(responses));
    }

    @Operation(summary = "건강 상태 수정", description = "ID를 통해 특정 건강 상태를 수정합니다.")
    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<HealthConditionResponse>> updateHealthCondition(@PathVariable Long id, @Valid @RequestBody HealthConditionUpdateRequest request) {
        HealthConditionResponse response = HealthConditionResponse.from(healthConditionService.updateHealthCondition(id, request));
        return ResponseEntity.ok(BaseResponse.success(response));
    }

    @Operation(summary = "건강 상태 삭제", description = "ID를 통해 특정 건강 상태를 삭제합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> deleteHealthCondition(@PathVariable Long id) {
        healthConditionService.deleteHealthCondition(id);
        return ResponseEntity.noContent().build();
    }
}
