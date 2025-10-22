package com.babsim.babsimbackend.domain.health.controller;

import com.babsim.babsimbackend.common.dto.BaseResponse;
import com.babsim.babsimbackend.domain.health.dto.request.HealthConditionCreateRequest;
import com.babsim.babsimbackend.domain.health.dto.request.HealthConditionUpdateRequest;
import com.babsim.babsimbackend.domain.health.dto.response.HealthConditionResponse;
import com.babsim.babsimbackend.domain.health.entity.HealthCondition;
import com.babsim.babsimbackend.domain.health.enums.HealthConditionType;
import com.babsim.babsimbackend.domain.health.service.HealthConditionService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HealthConditionController.class)
class HealthConditionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private HealthConditionService healthConditionService;

    @DisplayName("given_HealthConditionCreateRequest_when_createHealthCondition_then_returns201Created")
    @Test
    void given_HealthConditionCreateRequest_when_createHealthCondition_then_returns201Created() throws Exception {
        // given
        HealthConditionCreateRequest request = new HealthConditionCreateRequest("고혈압", HealthConditionType.CHRONIC_DISEASE);
        HealthCondition healthCondition = HealthCondition.builder()
                .name(request.name())
                .type(request.type())
                .build();
        ReflectionTestUtils.setField(healthCondition, "id", 1L);
        when(healthConditionService.createHealthCondition(any(HealthConditionCreateRequest.class))).thenReturn(healthCondition);

        // when
        ResultActions actions = mockMvc.perform(post("/api/v1/health-conditions")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // then
        actions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.data.name").value("고혈압"))
                .andExpect(jsonPath("$.data.type").value("CHRONIC_DISEASE"));
    }

    @DisplayName("given_HealthConditionId_when_getHealthConditionById_then_returns200Ok")
    @Test
    void given_HealthConditionId_when_getHealthConditionById_then_returns200Ok() throws Exception {
        // given
        Long healthConditionId = 1L;
        HealthCondition healthCondition = HealthCondition.builder()
                .name("고혈압")
                .type(HealthConditionType.CHRONIC_DISEASE)
                .build();
        ReflectionTestUtils.setField(healthCondition, "id", healthConditionId);
        when(healthConditionService.getHealthConditionById(healthConditionId)).thenReturn(healthCondition);

        // when
        ResultActions actions = mockMvc.perform(get("/api/v1/health-conditions/{id}", healthConditionId)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("고혈압"))
                .andExpect(jsonPath("$.data.type").value("CHRONIC_DISEASE"));
    }

    @DisplayName("when_getAllHealthConditions_then_returns200Ok")
    @Test
    void when_getAllHealthConditions_then_returns200Ok() throws Exception {
        // given
        HealthCondition healthCondition1 = HealthCondition.builder().name("고혈압").type(HealthConditionType.CHRONIC_DISEASE).build();
        ReflectionTestUtils.setField(healthCondition1, "id", 1L);
        HealthCondition healthCondition2 = HealthCondition.builder().name("당뇨").type(HealthConditionType.DIABETES).build();
        ReflectionTestUtils.setField(healthCondition2, "id", 2L);
        List<HealthCondition> healthConditions = Arrays.asList(healthCondition1, healthCondition2);
        when(healthConditionService.getAllHealthConditions()).thenReturn(healthConditions);

        // when
        ResultActions actions = mockMvc.perform(get("/api/v1/health-conditions")
                .contentType(MediaType.APPLICATION_JSON));

        // then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.length()").value(2))
                .andExpect(jsonPath("$.data[0].name").value("고혈압"))
                .andExpect(jsonPath("$.data[1].name").value("당뇨"));
    }

    @DisplayName("given_HealthConditionIdAndUpdateRequest_when_updateHealthCondition_then_returns200Ok")
    @Test
    void given_HealthConditionIdAndUpdateRequest_when_updateHealthCondition_then_returns200Ok() throws Exception {
        // given
        Long healthConditionId = 1L;
        HealthConditionUpdateRequest request = new HealthConditionUpdateRequest("고혈압 (수정)", HealthConditionType.DIABETES);
        HealthCondition updatedHealthCondition = HealthCondition.builder()
                .name(request.name())
                .type(request.type())
                .build();
        ReflectionTestUtils.setField(updatedHealthCondition, "id", healthConditionId);
        when(healthConditionService.updateHealthCondition(eq(healthConditionId), any(HealthConditionUpdateRequest.class))).thenReturn(updatedHealthCondition);

        // when
        ResultActions actions = mockMvc.perform(put("/api/v1/health-conditions/{id}", healthConditionId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)));

        // then
        actions.andExpect(status().isOk())
                .andExpect(jsonPath("$.data.name").value("고혈압 (수정)"))
                .andExpect(jsonPath("$.data.type").value("DIABETES"));
    }

    @DisplayName("given_HealthConditionId_when_deleteHealthCondition_then_returns204NoContent")
    @Test
    void given_HealthConditionId_when_deleteHealthCondition_then_returns204NoContent() throws Exception {
        // given
        Long healthConditionId = 1L;
        doNothing().when(healthConditionService).deleteHealthCondition(healthConditionId);

        // when
        ResultActions actions = mockMvc.perform(delete("/api/v1/health-conditions/{id}", healthConditionId)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        actions.andExpect(status().isNoContent());
    }
}
