package com.babsim.babsimbackend.infrastructure.messaging;

// AI 생성: 알림 서비스 인터페이스
public interface NotificationService {

    /**
     * 지정된 대상에게 메시지를 전송합니다.
     * @param to      수신자 정보 (예: 이메일 주소)
     * @param subject 메시지 제목
     * @param body    메시지 본문
     */
    void sendMessage(String to, String subject, String body);
}
