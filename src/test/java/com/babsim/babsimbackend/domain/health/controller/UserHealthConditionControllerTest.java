package com.babsim.babsimbackend.domain.health.controller;

import com.babsim.babsimbackend.domain.health.dto.request.UserHealthConditionCreateRequest;
import com.babsim.babsimbackend.domain.health.dto.request.UserHealthConditionUpdateRequest;
import com.babsim.babsimbackend.domain.health.entity.HealthCondition;
import com.babsim.babsimbackend.domain.health.entity.UserHealthCondition;
import com.babsim.babsimbackend.domain.health.enums.HealthConditionType;
import com.babsim.babsimbackend.domain.health.service.UserHealthConditionService;
import com.babsim.babsimbackend.domain.user.entity.User;
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
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = UserHealthConditionController.class,
	excludeAutoConfiguration = SecurityAutoConfiguration.class)
class UserHealthConditionControllerTest {

	@Autowired
	private MockMvc mockMvc;
	@Autowired
	private ObjectMapper objectMapper;

	@MockBean
	private UserHealthConditionService userHealthConditionService;

	private User user;
	private HealthCondition healthCondition;
	private UserHealthCondition userHealthCondition;

	private UUID userId;
	private Long healthConditionId;
	private Long userHealthConditionId;

	@BeforeEach
	void setUp() {
		userId = UUID.randomUUID();
		healthConditionId = 1L;
		userHealthConditionId = 10L;

		user = mock(User.class);
		given(user.getId()).willReturn(userId);

		healthCondition = mock(HealthCondition.class);
		given(healthCondition.getId()).willReturn(healthConditionId);
		given(healthCondition.getName()).willReturn("당뇨");
		given(healthCondition.getType()).willReturn(HealthConditionType.DIABETES);

		userHealthCondition = mock(UserHealthCondition.class);
		given(userHealthCondition.getId()).willReturn(userHealthConditionId);
		given(userHealthCondition.getUser()).willReturn(user);
		given(userHealthCondition.getHealthCondition()).willReturn(healthCondition);
	}

	@Test
	@DisplayName("사용자 건강 상태 생성 API 호출 성공")
	void createUserHealthCondition_success() throws Exception {
		// Given
		UserHealthConditionCreateRequest request = new UserHealthConditionCreateRequest(userId, healthConditionId);
		given(userHealthConditionService.createUserHealthCondition(any(UserHealthConditionCreateRequest.class)))
			.willReturn(userHealthCondition);

		// When & Then
		mockMvc.perform(post("/api/v1/user-health-conditions")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.data.userId").value(userId.toString()))
			.andExpect(jsonPath("$.data.healthConditionName").value("당뇨"));

		then(userHealthConditionService).should(times(1)).createUserHealthCondition(any(UserHealthConditionCreateRequest.class));
	}

	@Test
	@DisplayName("사용자 건강 상태 단일 조회 API 호출 성공")
	void getUserHealthConditionById_success() throws Exception {
		// Given
		given(userHealthConditionService.getUserHealthConditionById(userHealthConditionId)).willReturn(userHealthCondition);

		// When & Then
		mockMvc.perform(get("/api/v1/user-health-conditions/{id}", userHealthConditionId))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.id").value(userHealthConditionId))
			.andExpect(jsonPath("$.data.userId").value(userId.toString()));

		then(userHealthConditionService).should(times(1)).getUserHealthConditionById(userHealthConditionId);
	}

	@Test
	@DisplayName("모든 사용자 건강 상태 조회 API 호출 성공")
	void getAllUserHealthConditions_success() throws Exception {
		// Given
		given(userHealthConditionService.getAllUserHealthConditions()).willReturn(List.of(userHealthCondition));

		// When & Then
		mockMvc.perform(get("/api/v1/user-health-conditions"))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.length()").value(1))
			.andExpect(jsonPath("$.data[0].userId").value(userId.toString()));

		then(userHealthConditionService).should(times(1)).getAllUserHealthConditions();
	}

	@Test
	@DisplayName("사용자 건강 상태 수정 API 호출 성공")
	void updateUserHealthCondition_success() throws Exception {
		// Given
		UUID newUserId = UUID.randomUUID();
		Long newHealthConditionId = 2L;

		User newUpdatedUser = mock(User.class);
		given(newUpdatedUser.getId()).willReturn(newUserId);

		HealthCondition newUpdatedHealthCondition = mock(HealthCondition.class);
		given(newUpdatedHealthCondition.getId()).willReturn(newHealthConditionId);
		given(newUpdatedHealthCondition.getName()).willReturn("고혈압");
		given(newUpdatedHealthCondition.getType()).willReturn(HealthConditionType.HYPERTENSION);

		UserHealthCondition updatedUserHealthCondition = mock(UserHealthCondition.class);
		given(updatedUserHealthCondition.getId()).willReturn(userHealthConditionId);
		given(updatedUserHealthCondition.getUser()).willReturn(newUpdatedUser);
		given(updatedUserHealthCondition.getHealthCondition()).willReturn(newUpdatedHealthCondition);

		UserHealthConditionUpdateRequest request = new UserHealthConditionUpdateRequest(newUserId, newHealthConditionId);

		given(userHealthConditionService.updateUserHealthCondition(eq(userHealthConditionId), any(UserHealthConditionUpdateRequest.class)))
			.willReturn(updatedUserHealthCondition);

		// When & Then
		mockMvc.perform(put("/api/v1/user-health-conditions/{id}", userHealthConditionId)
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(request)))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.data.userId").value(newUserId.toString()))
			.andExpect(jsonPath("$.data.healthConditionName").value("고혈압"));

		then(userHealthConditionService).should(times(1)).updateUserHealthCondition(eq(userHealthConditionId), any(UserHealthConditionUpdateRequest.class));
	}

	@Test
	@DisplayName("사용자 건강 상태 삭제 API 호출 성공")
	void deleteUserHealthCondition_success() throws Exception {
		// Given

		// When & Then
		mockMvc.perform(delete("/api/v1/user-health-conditions/{id}", userHealthConditionId))
			.andExpect(status().isNoContent());

		then(userHealthConditionService).should(times(1)).deleteUserHealthCondition(userHealthConditionId);
	}
}