package com.babsim.babsimbackend.domain.user.service;

import com.babsim.babsimbackend.domain.user.dto.request.UserDto;
import com.babsim.babsimbackend.domain.user.entity.User;
import com.babsim.babsimbackend.domain.user.enums.GoalType;
import com.babsim.babsimbackend.domain.user.exception.UserNotFoundException;
import com.babsim.babsimbackend.domain.user.repository.UserRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

	@Mock UserRepository userRepository;
	@InjectMocks UserService userService;

	@Test
	void create_returns_uuid_and_maps_fields() throws Exception {
		var req = new UserDto.Create("홍길동", 29, 'M', new BigDecimal("175.2"), new BigDecimal("71.5"), GoalType.GENERAL, null);

		when(userRepository.save(any(User.class))).thenAnswer(inv -> {
			User u = inv.getArgument(0);
			setId(u, UUID.randomUUID());
			return u;
		});

		UUID id = userService.create(req);

		assertThat(id).isNotNull();
		verify(userRepository, times(1)).save(any(User.class));
	}

	@Test
	void get_throws_when_not_found() {
		UUID id = UUID.randomUUID();
		when(userRepository.findById(id)).thenReturn(Optional.empty());
		assertThatThrownBy(() -> userService.get(id)).isInstanceOf(UserNotFoundException.class);
	}

	@Test
	void update_changes_only_non_null_fields() throws Exception {
		var existing = User.builder().name("이전").age(20).sex('F').goal(GoalType.GENERAL).build();
		setId(existing, UUID.randomUUID());
		when(userRepository.findById(existing.getId())).thenReturn(Optional.of(existing));

		var req = new UserDto.Update("새이름", null, null, null, null, GoalType.WEIGHT_GAIN, "tok");
		userService.update(existing.getId(), req);

		assertThat(existing.getName()).isEqualTo("새이름");
		assertThat(existing.getGoal()).isEqualTo(GoalType.WEIGHT_GAIN);
		assertThat(existing.getAge()).isEqualTo(20);
		assertThat(existing.getNotionAccessToken()).isEqualTo("tok");
	}

	@Test
	void delete_throws_when_not_exists() {
		UUID id = UUID.randomUUID();
		when(userRepository.findById(id)).thenReturn(Optional.empty());
		assertThatThrownBy(() -> userService.delete(id)).isInstanceOf(UserNotFoundException.class);
	}

	private static void setId(User u, UUID id) throws Exception {
		Field f = User.class.getDeclaredField("id");
		f.setAccessible(true);
		f.set(u, id);
	}
}
