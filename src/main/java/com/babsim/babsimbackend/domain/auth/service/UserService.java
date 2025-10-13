package com.babsim.babsimbackend.domain.auth.service;

import com.babsim.babsimbackend.domain.auth.dto.request.UserDto;
import com.babsim.babsimbackend.domain.auth.entity.User;
import com.babsim.babsimbackend.domain.auth.repository.UserRepository;

import jakarta.persistence.EntityNotFoundException;

import java.util.UUID;

import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
		userRepository.save(user);
		return user.getId();
	}

	@Transactional(readOnly = true)
	public UserDto.Response get(UUID id) {
		final User u = userRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("User not found"));
		return toResponse(u);
	}

	@Transactional(readOnly = true)
	public Page<UserDto.Response> list(Pageable pageable) {
		return userRepository.findAll(pageable).map(this::toResponse);
	}

	@Transactional
	public void update(UUID id, UserDto.Update req) {
		final User u = userRepository.findById(id)
			.orElseThrow(() -> new EntityNotFoundException("User not found"));

		// 필요한 필드만 부분 수정
		if (req.name() != null) {
			u.changeName(req.name());
		}
		if (req.age() != null) {
			u.changeAge(req.age());
		}
		if (req.sex() != null) {
			u.changeSex(req.sex());
		}
		if (req.height() != null) {
			u.changeHeight(req.height());
		}
		if (req.weight() != null) {
			u.changeWeight(req.weight());
		}
		if (req.goal() != null) {
			u.changeGoal(req.goal());
		}
		if (req.notionAccessToken() != null) {
			u.changeNotionAccessToken(req.notionAccessToken());
		}
	}

	@Transactional
	public void delete(UUID id) {
		if (!userRepository.existsById(id)) {
			throw new EntityNotFoundException("User not found");
		}
		userRepository.deleteById(id);
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
