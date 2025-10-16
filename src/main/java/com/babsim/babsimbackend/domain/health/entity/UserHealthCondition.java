package com.babsim.babsimbackend.domain.health.entity;

import com.babsim.babsimbackend.common.entity.BaseEntity;
import com.babsim.babsimbackend.domain.auth.entity.User;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

// auth 테스트 용도
@Entity
@Table(name = "user_health_conditions")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserHealthCondition extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "health_condition_id", nullable = false)
    private HealthCondition healthCondition;

    @Builder
    public UserHealthCondition(User user, HealthCondition healthCondition) {
        this.user = user;
        this.healthCondition = healthCondition;
    }
}
