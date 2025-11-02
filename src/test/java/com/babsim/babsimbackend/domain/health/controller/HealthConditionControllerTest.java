package com.babsim.babsimbackend.domain.health.controller;

import com.babsim.babsimbackend.domain.health.dto.request.HealthConditionCreateRequest;
import com.babsim.babsimbackend.domain.health.dto.request.HealthConditionUpdateRequest;
import com.babsim.babsimbackend.domain.health.entity.HealthCondition;
import com.babsim.babsimbackend.domain.health.enums.HealthConditionType;
import com.babsim.babsimbackend.domain.health.service.HealthConditionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = HealthConditionController.class,
	excludeAutoConfiguration = SecurityAutoConfiguration.class)
class HealthConditionControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private HealthConditionService healthConditionService;

	private HealthCondition healthCondition;
	private Long healthConditionId;

	@BeforeEach
	void setUp() {
		healthConditionId = 1L;
		healthCondition = mock(HealthCondition.class);

		given(healthCondition.getId()).willReturn(healthConditionId);
		given(healthCondition.getName()).willReturn("당뇨");
		given(healthCondition.getType()).willReturn(HealthConditionType.DIABETES);
	}

	@Test
	@DisplayName("건강 상태 생성 API 호출 성공")
	void createHealthCondition_success() throws Exception {
		// Given
		HealthConditionCreateRequest request = new HealthConditionCreateRequest("당뇨", HealthConditionType.DIABETES);
		given(healthConditionService.createHealthCondition(any(HealthConditionCreateRequest.class))).willReturn(healthCondition);

		// When & Then
		mockMvc.perform(post("/api/v1/health-conditions")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.data.name").value("당뇨"))
			.andExpect(jsonPath("$.data.type").value("DIABETES"));

		then(healthConditionService).should(times(1)).createHealthCondition(any(HealthConditionCreateRequest.class));
	}

	@Test
	@DisplayName("건강 상태 단일 조회 API 호출 성공")
	void getHealthConditionById_success() throws Exception {
		// Given
		given(healthConditionService.getHealthConditionById(healthConditionId)).willReturn(healthCondition);

		// When & Then
		mockMvc.perform(get("/api/v1/health-conditions/{id}", healthConditionId))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.name").value("당뇨"));

		then(healthConditionService).should(times(1)).getHealthConditionById(healthConditionId);
	}

	@Test
	@DisplayName("모든 건강 상태 조회 API 호출 성공")
	void getAllHealthConditions_success() throws Exception {
		// Given
		given(healthConditionService.getAllHealthConditions()).willReturn(List.of(healthCondition));

		// When & Then
		mockMvc.perform(get("/api/v1/health-conditions"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.length()").value(1))
			.andExpect(jsonPath("$.data[0].name").value("당뇨"));

		then(healthConditionService).should(times(1)).getAllHealthConditions();
	}

	@Test
	@DisplayName("건강 상태 수정 API 호출 성공")
	void updateHealthCondition_success() throws Exception {
		// Given
		HealthConditionUpdateRequest request = new HealthConditionUpdateRequest("고혈압", HealthConditionType.HYPERTENSION);

		HealthCondition updatedHealthCondition = mock(HealthCondition.class);
		given(updatedHealthCondition.getId()).willReturn(healthConditionId);
		given(updatedHealthCondition.getName()).willReturn("고혈압");
		given(updatedHealthCondition.getType()).willReturn(HealthConditionType.HYPERTENSION);

		given(healthConditionService.updateHealthCondition(eq(healthConditionId), any(HealthConditionUpdateRequest.class)))
			.willReturn(updatedHealthCondition);

		// When & Then
		mockMvc.perform(put("/api/v1/health-conditions/{id}", healthConditionId)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.name").value("고혈압"))
			.andExpect(jsonPath("$.data.type").value("HYPERTENSION"));

		then(healthConditionService).should(times(1)).updateHealthCondition(eq(healthConditionId), any(HealthConditionUpdateRequest.class));
	}

	@Test
	@DisplayName("건강 상태 삭제 API 호출 성공")
	void deleteHealthCondition_success() throws Exception {
		// Given

		// When & Then
		mockMvc.perform(delete("/api/v1/health-conditions/{id}", healthConditionId))
			.andExpect(status().isNoContent());

		then(healthConditionService).should(times(1)).deleteHealthCondition(healthConditionId);
	}
}