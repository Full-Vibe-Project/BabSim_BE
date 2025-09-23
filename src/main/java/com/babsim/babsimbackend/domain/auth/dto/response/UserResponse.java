package com.babsim.babsimbackend.domain.auth.dto.response;

import com.babsim.babsimbackend.domain.auth.entity.User;
import lombok.Builder;
import lombok.Getter;

// AI 생성: 사용자 정보 응답 DTO
@Getter
public class UserResponse {
    private final Long id;
    private final String email;
    private final String name;

    @Builder
    public UserResponse(Long id, String email, String name) {
        this.id = id;
        this.email = email;
        this.name = name;
    }

    public static UserResponse from(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }
}
