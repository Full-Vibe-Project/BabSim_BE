package com.babsim.babsimbackend.domain.user.service;

import com.babsim.babsimbackend.domain.user.dto.request.UserDto;
import com.babsim.babsimbackend.domain.user.entity.User;
import com.babsim.babsimbackend.domain.user.exception.UserNotFoundException;
import com.babsim.babsimbackend.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public UUID create(UserDto.Create req) {
        final User user = User.builder()
            .name(req.name())
            .age(req.age())
            .gender(req.gender())
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
        return toResponse(findUserById(id));
    }

    @Transactional(readOnly = true)
    public User findUserById(UUID id) {
        return userRepository.findById(id)
            .orElseThrow(UserNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public Page<UserDto.Response> list(Pageable pageable) {
        return userRepository.findAll(pageable).map(this::toResponse);
    }

    @Transactional
    public void update(UUID id, UserDto.Update req) {
        findUserById(id).update(req);
    }

    @Transactional
    public void delete(UUID id) {
        userRepository.delete(findUserById(id));
    }

    private UserDto.Response toResponse(User u) {
        return new UserDto.Response(
            u.getId(),
            u.getName(),
            u.getAge(),
            u.getGender(),
            u.getHeight(),
            u.getWeight(),
            u.getGoal(),
            u.getNotionAccessToken()
        );
    }
}
