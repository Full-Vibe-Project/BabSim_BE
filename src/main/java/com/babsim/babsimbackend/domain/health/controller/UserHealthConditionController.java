package com.babsim.babsimbackend.domain.health.controller;

import com.babsim.babsimbackend.common.dto.BaseResponse;
import com.babsim.babsimbackend.domain.health.dto.request.UserHealthConditionCreateRequest;
import com.babsim.babsimbackend.domain.health.dto.request.UserHealthConditionUpdateRequest;
import com.babsim.babsimbackend.domain.health.dto.response.UserHealthConditionResponse;
import com.babsim.babsimbackend.domain.health.service.UserHealthConditionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "User Health Condition", description = "사용자 건강 상태 관련 API")
@RestController
@RequestMapping("/api/v1/user-health-conditions")
@RequiredArgsConstructor
public class UserHealthConditionController {

    private final UserHealthConditionService userHealthConditionService;

    @Operation(summary = "사용자 건강 상태 생성", description = "새로운 사용자 건강 상태를 생성합니다.")
    @PostMapping
    public ResponseEntity<BaseResponse<UserHealthConditionResponse>> createUserHealthCondition(@Valid @RequestBody UserHealthConditionCreateRequest request) {
        UserHealthConditionResponse response = UserHealthConditionResponse.from(userHealthConditionService.createUserHealthCondition(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(BaseResponse.success(response));
    }

    @Operation(summary = "사용자 건강 상태 단일 조회", description = "ID를 통해 특정 사용자 건강 상태를 조회합니다.")
    @GetMapping("/{id}")
    public ResponseEntity<BaseResponse<UserHealthConditionResponse>> getUserHealthConditionById(@PathVariable Long id) {
        UserHealthConditionResponse response = UserHealthConditionResponse.from(userHealthConditionService.getUserHealthConditionById(id));
        return ResponseEntity.ok(BaseResponse.success(response));
    }

    @Operation(summary = "모든 사용자 건강 상태 조회", description = "모든 사용자 건강 상태 목록을 조회합니다.")
    @GetMapping
    public ResponseEntity<BaseResponse<List<UserHealthConditionResponse>>> getAllUserHealthConditions() {
        List<UserHealthConditionResponse> responses = userHealthConditionService.getAllUserHealthConditions().stream()
                .map(UserHealthConditionResponse::from)
                .collect(Collectors.toList());
        return ResponseEntity.ok(BaseResponse.success(responses));
    }

    @Operation(summary = "사용자 건강 상태 수정", description = "ID를 통해 특정 사용자 건강 상태를 수정합니다.")
    @PutMapping("/{id}")
    public ResponseEntity<BaseResponse<UserHealthConditionResponse>> updateUserHealthCondition(@PathVariable Long id, @Valid @RequestBody UserHealthConditionUpdateRequest request) {
        UserHealthConditionResponse response = UserHealthConditionResponse.from(userHealthConditionService.updateUserHealthCondition(id, request));
        return ResponseEntity.ok(BaseResponse.success(response));
    }

    @Operation(summary = "사용자 건강 상태 삭제", description = "ID를 통해 특정 사용자 건강 상태를 삭제합니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<BaseResponse<Void>> deleteUserHealthCondition(@PathVariable Long id) {
        userHealthConditionService.deleteUserHealthCondition(id);
        return ResponseEntity.noContent().build();
    }
}
