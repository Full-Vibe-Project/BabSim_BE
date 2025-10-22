package com.babsim.babsimbackend.domain.health.service;

import com.babsim.babsimbackend.domain.health.dto.request.UserHealthConditionCreateRequest;
import com.babsim.babsimbackend.domain.health.dto.request.UserHealthConditionUpdateRequest;
import com.babsim.babsimbackend.domain.health.entity.HealthCondition;
import com.babsim.babsimbackend.domain.health.entity.UserHealthCondition;
import com.babsim.babsimbackend.domain.health.enums.HealthConditionType;
import com.babsim.babsimbackend.domain.health.exception.HealthConditionNotFoundException;
import com.babsim.babsimbackend.domain.health.exception.UserHealthConditionNotFoundException;
import com.babsim.babsimbackend.domain.health.repository.HealthConditionRepository;
import com.babsim.babsimbackend.domain.health.repository.UserHealthConditionRepository;
import com.babsim.babsimbackend.domain.user.entity.User;
import com.babsim.babsimbackend.domain.user.enums.Gender;
import com.babsim.babsimbackend.domain.user.enums.GoalType;
import com.babsim.babsimbackend.domain.user.exception.UserNotFoundException;
import com.babsim.babsimbackend.domain.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserHealthConditionServiceTest {

    @Mock
    private UserHealthConditionRepository userHealthConditionRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private HealthConditionRepository healthConditionRepository;

    @InjectMocks
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

    @DisplayName("given_UserHealthConditionCreateRequest_when_createUserHealthCondition_then_returnsSavedUserHealthCondition")
    @Test
    void given_UserHealthConditionCreateRequest_when_createUserHealthCondition_then_returnsSavedUserHealthCondition() {
        // given
        UserHealthConditionCreateRequest request = new UserHealthConditionCreateRequest(user.getId(), healthCondition.getId());
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(healthConditionRepository.findById(healthCondition.getId())).thenReturn(Optional.of(healthCondition));
        when(userHealthConditionRepository.save(any(UserHealthCondition.class))).thenReturn(userHealthCondition);

        // when
        UserHealthCondition savedUserHealthCondition = userHealthConditionService.createUserHealthCondition(request);

        // then
        assertThat(savedUserHealthCondition).isNotNull();
        assertThat(savedUserHealthCondition.getId()).isEqualTo(1L);
        assertThat(savedUserHealthCondition.getUser().getId()).isEqualTo(user.getId());
        assertThat(savedUserHealthCondition.getHealthCondition().getId()).isEqualTo(healthCondition.getId());
        verify(userRepository, times(1)).findById(user.getId());
        verify(healthConditionRepository, times(1)).findById(healthCondition.getId());
        verify(userHealthConditionRepository, times(1)).save(any(UserHealthCondition.class));
    }

    @DisplayName("given_UserHealthConditionId_when_getUserHealthConditionById_then_returnsUserHealthCondition")
    @Test
    void given_UserHealthConditionId_when_getUserHealthConditionById_then_returnsUserHealthCondition() {
        // given
        Long userHealthConditionId = 1L;
        when(userHealthConditionRepository.findById(userHealthConditionId)).thenReturn(Optional.of(userHealthCondition));

        // when
        UserHealthCondition foundUserHealthCondition = userHealthConditionService.getUserHealthConditionById(userHealthConditionId);

        // then
        assertThat(foundUserHealthCondition).isNotNull();
        assertThat(foundUserHealthCondition.getId()).isEqualTo(userHealthConditionId);
        verify(userHealthConditionRepository, times(1)).findById(userHealthConditionId);
    }

    @DisplayName("given_nonExistingUserHealthConditionId_when_getUserHealthConditionById_then_throwsException")
    @Test
    void given_nonExistingUserHealthConditionId_when_getUserHealthConditionById_then_throwsException() {
        // given
        Long nonExistingId = 99L;
        when(userHealthConditionRepository.findById(nonExistingId)).thenReturn(Optional.empty());

        // when & then
        assertThrows(UserHealthConditionNotFoundException.class, () -> userHealthConditionService.getUserHealthConditionById(nonExistingId));
        verify(userHealthConditionRepository, times(1)).findById(nonExistingId);
    }

    @DisplayName("when_getAllUserHealthConditions_then_returnsAllUserHealthConditions")
    @Test
    void when_getAllUserHealthConditions_then_returnsAllUserHealthConditions() {
        // given
        UserHealthCondition userHealthCondition2 = UserHealthCondition.builder()
                .user(user)
                .healthCondition(healthCondition)
                .build();
        ReflectionTestUtils.setField(userHealthCondition2, "id", 2L);
        List<UserHealthCondition> userHealthConditions = Arrays.asList(userHealthCondition, userHealthCondition2);
        when(userHealthConditionRepository.findAll()).thenReturn(userHealthConditions);

        // when
        List<UserHealthCondition> foundUserHealthConditions = userHealthConditionService.getAllUserHealthConditions();

        // then
        assertThat(foundUserHealthConditions).hasSize(2);
        assertThat(foundUserHealthConditions).containsExactlyInAnyOrder(userHealthCondition, userHealthCondition2);
        verify(userHealthConditionRepository, times(1)).findAll();
    }

    @DisplayName("given_UserHealthConditionIdAndUpdateRequest_when_updateUserHealthCondition_then_returnsUpdatedUserHealthCondition")
    @Test
    void given_UserHealthConditionIdAndUpdateRequest_when_updateUserHealthCondition_then_returnsUpdatedUserHealthCondition() {
        // given
        Long userHealthConditionId = 1L;
        UserHealthConditionUpdateRequest request = new UserHealthConditionUpdateRequest(user.getId(), healthCondition.getId());
        when(userHealthConditionRepository.findById(userHealthConditionId)).thenReturn(Optional.of(userHealthCondition));
        when(userRepository.findById(user.getId())).thenReturn(Optional.of(user));
        when(healthConditionRepository.findById(healthCondition.getId())).thenReturn(Optional.of(healthCondition));
        when(userHealthConditionRepository.save(any(UserHealthCondition.class))).thenReturn(userHealthCondition);

        // when
        UserHealthCondition updatedUserHealthCondition = userHealthConditionService.updateUserHealthCondition(userHealthConditionId, request);

        // then
        assertThat(updatedUserHealthCondition).isNotNull();
        assertThat(updatedUserHealthCondition.getId()).isEqualTo(userHealthConditionId);
        assertThat(updatedUserHealthCondition.getUser().getId()).isEqualTo(user.getId());
        assertThat(updatedUserHealthCondition.getHealthCondition().getId()).isEqualTo(healthCondition.getId());
        verify(userHealthConditionRepository, times(1)).findById(userHealthConditionId);
        verify(userRepository, times(1)).findById(user.getId());
        verify(healthConditionRepository, times(1)).findById(healthCondition.getId());
        verify(userHealthConditionRepository, times(1)).save(any(UserHealthCondition.class));
    }

    @DisplayName("given_UserHealthConditionId_when_deleteUserHealthCondition_then_deletesUserHealthCondition")
    @Test
    void given_UserHealthConditionId_when_deleteUserHealthCondition_then_deletesUserHealthCondition() {
        // given
        Long userHealthConditionId = 1L;
        doNothing().when(userHealthConditionRepository).deleteById(userHealthConditionId);

        // when
        userHealthConditionService.deleteUserHealthCondition(userHealthConditionId);

        // then
        verify(userHealthConditionRepository, times(1)).deleteById(userHealthConditionId);
    }
}
