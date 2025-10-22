package com.babsim.babsimbackend.domain.health.service;

import com.babsim.babsimbackend.domain.health.dto.request.HealthConditionCreateRequest;
import com.babsim.babsimbackend.domain.health.dto.request.HealthConditionUpdateRequest;
import com.babsim.babsimbackend.domain.health.entity.HealthCondition;
import com.babsim.babsimbackend.domain.health.enums.HealthConditionType;
import com.babsim.babsimbackend.domain.health.exception.HealthConditionNotFoundException;
import com.babsim.babsimbackend.domain.health.repository.HealthConditionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HealthConditionServiceTest {

    @Mock
    private HealthConditionRepository healthConditionRepository;

    @InjectMocks
    private HealthConditionService healthConditionService;

    private HealthCondition healthCondition;

    @BeforeEach
    void setUp() {
        healthCondition = HealthCondition.builder()
                .name("고혈압")
                .type(HealthConditionType.CHRONIC_DISEASE)
                .build();
        ReflectionTestUtils.setField(healthCondition, "id", 1L);
    }

    @DisplayName("given_HealthConditionCreateRequest_when_createHealthCondition_then_returnsSavedHealthCondition")
    @Test
    void given_HealthConditionCreateRequest_when_createHealthCondition_then_returnsSavedHealthCondition() {
        // given
        HealthConditionCreateRequest request = new HealthConditionCreateRequest("고혈압", HealthConditionType.CHRONIC_DISEASE);
        when(healthConditionRepository.save(any(HealthCondition.class))).thenReturn(healthCondition);

        // when
        HealthCondition savedHealthCondition = healthConditionService.createHealthCondition(request);

        // then
        assertThat(savedHealthCondition).isNotNull();
        assertThat(savedHealthCondition.getId()).isEqualTo(1L);
        assertThat(savedHealthCondition.getName()).isEqualTo(request.name());
        assertThat(savedHealthCondition.getType()).isEqualTo(request.type());
        verify(healthConditionRepository, times(1)).save(any(HealthCondition.class));
    }

    @DisplayName("given_HealthConditionId_when_getHealthConditionById_then_returnsHealthCondition")
    @Test
    void given_HealthConditionId_when_getHealthConditionById_then_returnsHealthCondition() {
        // given
        Long healthConditionId = 1L;
        when(healthConditionRepository.findById(healthConditionId)).thenReturn(Optional.of(healthCondition));

        // when
        HealthCondition foundHealthCondition = healthConditionService.getHealthConditionById(healthConditionId);

        // then
        assertThat(foundHealthCondition).isNotNull();
        assertThat(foundHealthCondition.getId()).isEqualTo(healthConditionId);
        verify(healthConditionRepository, times(1)).findById(healthConditionId);
    }

    @DisplayName("given_nonExistingHealthConditionId_when_getHealthConditionById_then_throwsException")
    @Test
    void given_nonExistingHealthConditionId_when_getHealthConditionById_then_throwsException() {
        // given
        Long nonExistingId = 99L;
        when(healthConditionRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        // when & then
        assertThrows(HealthConditionNotFoundException.class, () -> healthConditionService.getHealthConditionById(nonExistingId));
        verify(healthConditionRepository, times(1)).findById(nonExistingId);
    }

    @DisplayName("when_getAllHealthConditions_then_returnsAllHealthConditions")
    @Test
    void when_getAllHealthConditions_then_returnsAllHealthConditions() {
        // given
        HealthCondition healthCondition2 = HealthCondition.builder()
                .name("당뇨")
                .type(HealthConditionType.DIABETES)
                .build();
        ReflectionTestUtils.setField(healthCondition2, "id", 2L);
        List<HealthCondition> healthConditions = Arrays.asList(healthCondition, healthCondition2);
        when(healthConditionRepository.findAll()).thenReturn(healthConditions);

        // when
        List<HealthCondition> foundHealthConditions = healthConditionService.getAllHealthConditions();

        // then
        assertThat(foundHealthConditions).hasSize(2);
        assertThat(foundHealthConditions).containsExactlyInAnyOrder(healthCondition, healthCondition2);
        verify(healthConditionRepository, times(1)).findAll();
    }

    @DisplayName("given_HealthConditionIdAndUpdateRequest_when_updateHealthCondition_then_returnsUpdatedHealthCondition")
    @Test
    void given_HealthConditionIdAndUpdateRequest_when_updateHealthCondition_then_returnsUpdatedHealthCondition() {
        // given
        Long healthConditionId = 1L;
        HealthConditionUpdateRequest request = new HealthConditionUpdateRequest("고혈압 (수정)", HealthConditionType.DIABETES);
        when(healthConditionRepository.findById(healthConditionId)).thenReturn(Optional.of(healthCondition));
        when(healthConditionRepository.save(any(HealthCondition.class))).thenReturn(healthCondition);

        // when
        HealthCondition updatedHealthCondition = healthConditionService.updateHealthCondition(healthConditionId, request);

        // then
        assertThat(updatedHealthCondition).isNotNull();
        assertThat(updatedHealthCondition.getId()).isEqualTo(healthConditionId);
        assertThat(updatedHealthCondition.getName()).isEqualTo(request.name());
        assertThat(updatedHealthCondition.getType()).isEqualTo(request.type());
        verify(healthConditionRepository, times(1)).findById(healthConditionId);
        verify(healthConditionRepository, times(1)).save(any(HealthCondition.class));
    }

    @DisplayName("given_HealthConditionId_when_deleteHealthCondition_then_deletesHealthCondition")
    @Test
    void given_HealthConditionId_when_deleteHealthCondition_then_deletesHealthCondition() {
        // given
        Long healthConditionId = 1L;
        doNothing().when(healthConditionRepository).deleteById(healthConditionId);

        // when
        healthConditionService.deleteHealthCondition(healthConditionId);

        // then
        verify(healthConditionRepository, times(1)).deleteById(healthConditionId);
    }
}
