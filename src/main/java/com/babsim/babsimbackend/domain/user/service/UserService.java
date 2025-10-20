package com.babsim.babsimbackend.domain.user.service;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.babsim.babsimbackend.domain.user.dto.request.UserDto;
import com.babsim.babsimbackend.domain.user.entity.User;
import com.babsim.babsimbackend.domain.user.exception.UserNotFoundException;
import com.babsim.babsimbackend.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	@Transactional
	public UUID create(UserDto.Create req) {
		final User user = User.builder()
			.name(req.name())
			.age(req.age())
			.sex(req.sex())
			.height(req.height())
			.weight(req.weight())
			.goal(req.goal())
			.notionAccessToken(req.notionAccessToken())
			.build();
		final User savedUser = userRepository.save(user);
		return savedUser.getId();
	}

	@Transactional(readOnly = true)
	public UserDto.Response get(UUID id) {
		final User u = userRepository.findById(id)
			.orElseThrow(UserNotFoundException::new);
		return toResponse(u);
	}

	@Transactional(readOnly = true)
	public Page<UserDto.Response> list(Pageable pageable) {
		return userRepository.findAll(pageable).map(this::toResponse);
	}

	@Transactional
	public void update(UUID id, UserDto.Update req) {
		final User u = userRepository.findById(id)
			.orElseThrow(UserNotFoundException::new);
		u.update(req); // 엔티티에 업데이트 위임
	}

	@Transactional
	public void delete(UUID id) {
		User user = userRepository.findById(id)
			.orElseThrow(UserNotFoundException::new);
		userRepository.delete(user);
	}

	private UserDto.Response toResponse(User u) {
		return new UserDto.Response(
			u.getId(),
			u.getName(),
			u.getAge(),
			u.getSex(),
			u.getHeight(),
			u.getWeight(),
			u.getGoal(),
			u.getNotionAccessToken()
		);
	}
}
