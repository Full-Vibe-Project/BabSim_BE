package com.babsim.babsimbackend.domain.timeseries.aggregation;

import com.babsim.babsimbackend.domain.user.entity.User;
import com.babsim.babsimbackend.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DailyAggregationSchedulerTest {

    @InjectMocks
    private DailyAggregationScheduler dailyAggregationScheduler;

    @Mock
    private UserRepository userRepository;

    @Mock
    private DailyNutritionAggregationService dailyNutritionAggregationService;

    @Test
    @DisplayName("스케줄러가 모든 사용자에 대해 전날의 영양 데이터를 집계하고 저장한다")
    void whenScheduleDailyAggregation_thenServiceIsCalledForAllUsersForPreviousDay() {
        // given
        UUID user1Id = UUID.randomUUID();
        User user1 = User.builder().name("User1").build();
        ReflectionTestUtils.setField(user1, "id", user1Id);

        UUID user2Id = UUID.randomUUID();
        User user2 = User.builder().name("User2").build();
        ReflectionTestUtils.setField(user2, "id", user2Id);

        List<User> users = Arrays.asList(user1, user2);
        when(userRepository.findAll()).thenReturn(users);

        LocalDate previousDay = LocalDate.now().minusDays(1);

        // when
        dailyAggregationScheduler.scheduleDailyAggregation();

        // then
        verify(userRepository, times(1)).findAll();
        verify(dailyNutritionAggregationService, times(1)).aggregateAndSave(eq(user1), eq(previousDay));
        verify(dailyNutritionAggregationService, times(1)).aggregateAndSave(eq(user2), eq(previousDay));
    }
}
