//package com.babsim.babsimbackend.domain.recommendation.entity;
//
//import com.babsim.babsimbackend.common.entity.BaseEntity;
//import jakarta.persistence.*;
//import lombok.AccessLevel;
//import lombok.Builder;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//
//// AI 생성: 추천 엔티티
//@Entity
//@Table(name = "recommendations")
//@Getter
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
//public class Recommendation extends BaseEntity {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @Column(nullable = false)
//    private Long userId;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "menu_id")
//    private Menu menu;
//
//    @Column(nullable = false)
//    private String reason; // 추천 사유
//
//    @Builder
//    public Recommendation(Long userId, Menu menu, String reason) {
//        this.userId = userId;
//        this.menu = menu;
//        this.reason = reason;
//    }
//}
