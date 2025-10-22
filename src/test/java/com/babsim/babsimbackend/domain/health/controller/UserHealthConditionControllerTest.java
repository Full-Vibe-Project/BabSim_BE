package com.babsim.babsimbackend.domain.health.controller;

import com.babsim.babsimbackend.common.dto.BaseResponse;
import com.babsim.babsimbackend.domain.health.dto.request.UserHealthConditionCreateRequest;
import com.babsim.babsimbackend.domain.health.dto.request.UserHealthConditionUpdateRequest;
import com.babsim.babsimbackend.domain.health.dto.response.UserHealthConditionResponse;
import com.babsim.babsimbackend.domain.health.entity.HealthCondition;
import com.babsim.babsimbackend.domain.health.entity.UserHealthCondition;
import com.babsim.babsimbackend.domain.health.enums.HealthConditionType;
import com.babsim.babsimbackend.domain.health.service.UserHealthConditionService;
import com.babsim.babsimbackend.domain.user.entity.User;
import com.babsim.babsimbackend.domain.user.enums.Gender;
import com.babsim.babsimbackend.domain.user.enums.GoalType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserHealthConditionController.class)
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

    @BeforeEach
    void setUp() {
        user = User.builder()
                .name("testUser")
                .gender(Gender.MALE)
                .age(30)
                .height(new BigDecimal("170.0"))
                .weight(new BigDecimal("60.0"))
                .goal(GoalType.WEIGHT_LOSS)
                .build();
        ReflectionTestUtils.setField(user, "id", UUID.randomUUID());

        healthCondition = HealthCondition.builder()
                .name("고혈압")
                .type(HealthConditionType.CHRONIC_DISEASE)
                .build();
        ReflectionTestUtils.setField(healthCondition, "id", 1L);

        userHealthCondition = UserHealthCondition.builder()
                .user(user)
                .healthCondition(healthCondition)
                .build();
        ReflectionTestUtils.setField(userHealthCondition, "id", 1L);
    }

    @DisplayName("given_UserHealthConditionCreateRequest_when_createUserHealthCondition_then_returns201Created")
    @Test
    void given_UserHealthConditionCreateRequest_when_createUserHealthCondition_then_returns201Created() throws Exception {
        // given
        UserHealthConditionCreateRequest request = new UserHealthConditionCreateRequest(user.getId(), healthCondition.getId());
        when(userHealthConditionService.createUserHealthCondition(any(UserHealthConditionCreateRequest.class))).thenReturn(userHealthCondition);

        // when
        ResultActions actions = mockMvc.perform(post("/api/v1/user-health-conditions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // then
        actions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.userId").value(user.getId().toString()))
                .andExpect(jsonPath("$.data.healthConditionId").value(healthCondition.getId()));
    }

    @DisplayName("given_UserHealthConditionId_when_getUserHealthConditionById_then_returns200Ok")
    @Test
    void given_UserHealthConditionId_when_getUserHealthConditionById_then_returns200Ok() throws Exception {
        // given
        Long userHealthConditionId = 1L;
        when(userHealthConditionService.getUserHealthConditionById(userHealthConditionId)).thenReturn(userHealthCondition);

        // when
        ResultActions actions = mockMvc.perform(get("/api/v1/user-health-conditions/{id}", userHealthConditionId)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.userId").value(user.getId().toString()))
                .andExpect(jsonPath("$.data.healthConditionId").value(healthCondition.getId()));
    }

    @DisplayName("when_getAllUserHealthConditions_then_returns200Ok")
    @Test
    void when_getAllUserHealthConditions_then_returns200Ok() throws Exception {
        // given
        UserHealthCondition userHealthCondition2 = UserHealthCondition.builder()
                .user(user)
                .healthCondition(healthCondition)
                .build();
        ReflectionTestUtils.setField(userHealthCondition2, "id", 2L);
        List<UserHealthCondition> userHealthConditions = Arrays.asList(userHealthCondition, userHealthCondition2);
        when(userHealthConditionService.getAllUserHealthConditions()).thenReturn(userHealthConditions);

        // when
        ResultActions actions = mockMvc.perform(get("/api/v1/user-health-conditions")
                .contentType(MediaType.APPLICATION_JSON));

        // then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].userId").value(user.getId().toString()))
                .andExpect(jsonPath("$.data[1].healthConditionId").value(healthCondition.getId()));
    }

    @DisplayName("given_UserHealthConditionIdAndUpdateRequest_when_updateUserHealthCondition_then_returns200Ok")
    @Test
    void given_UserHealthConditionIdAndUpdateRequest_when_updateUserHealthCondition_then_returns200Ok() throws Exception {
        // given
        Long userHealthConditionId = 1L;
        UserHealthConditionUpdateRequest request = new UserHealthConditionUpdateRequest(user.getId(), healthCondition.getId());
        when(userHealthConditionService.updateUserHealthCondition(eq(userHealthConditionId), any(UserHealthConditionUpdateRequest.class))).thenReturn(userHealthCondition);

        // when
        ResultActions actions = mockMvc.perform(put("/api/v1/user-health-conditions/{id}", userHealthConditionId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.userId").value(user.getId().toString()))
                .andExpect(jsonPath("$.data.healthConditionId").value(healthCondition.getId()));
    }

    @DisplayName("given_UserHealthConditionId_when_deleteUserHealthCondition_then_returns204NoContent")
    @Test
    void given_UserHealthConditionId_when_deleteUserHealthCondition_then_returns204NoContent() throws Exception {
        // given
        Long userHealthConditionId = 1L;
        doNothing().when(userHealthConditionService).deleteUserHealthCondition(userHealthConditionId);

        // when
        ResultActions actions = mockMvc.perform(delete("/api/v1/user-health-conditions/{id}", userHealthConditionId)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        actions.andExpect(status().isNoContent());
    }
}
