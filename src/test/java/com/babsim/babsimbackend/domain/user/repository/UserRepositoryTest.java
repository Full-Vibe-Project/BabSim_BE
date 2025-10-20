package com.babsim.babsimbackend.domain.user.repository;

import com.babsim.babsimbackend.domain.user.dto.request.UserDto;
import com.babsim.babsimbackend.domain.user.entity.User;
import com.babsim.babsimbackend.domain.user.enums.GoalType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class UserRepositoryTest {

	@Autowired UserRepository userRepository;

	@Test
	@DisplayName("User CRUD: save → findById → update(dirty) → delete")
	void user_crud() {
		// create
		var u = User.builder()
			.name("홍길동")
			.age(29)
			.sex('M')
			.height(new BigDecimal("175.2"))
			.weight(new BigDecimal("71.5"))
			.goal(GoalType.GENERAL)
			.build();
		var saved = userRepository.save(u);
		assertThat(saved.getId()).isNotNull();

		// read
		var found = userRepository.findById(saved.getId()).orElseThrow();
		assertThat(found.getName()).isEqualTo("홍길동");

		// update (더티체킹)
		var updateDto = new UserDto.Update("홍길동2", null, null, null, null, null, null);
		found.update(updateDto);
		userRepository.flush();
		var updated = userRepository.findById(saved.getId()).orElseThrow();
		assertThat(updated.getName()).isEqualTo("홍길동2");

		// delete
		userRepository.delete(updated);
		userRepository.flush();
		assertThat(userRepository.findById(saved.getId())).isEmpty();
	}
}
