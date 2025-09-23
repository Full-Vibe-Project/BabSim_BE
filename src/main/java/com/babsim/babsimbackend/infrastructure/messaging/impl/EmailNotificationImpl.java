package com.babsim.babsimbackend.infrastructure.messaging.impl;

import com.babsim.babsimbackend.infrastructure.messaging.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

// AI 생성: 이메일 알림 구현체
@Service
@RequiredArgsConstructor
public class EmailNotificationImpl implements NotificationService {

    // private final JavaMailSender emailSender;

    @Override
    public void sendMessage(String to, String subject, String body) {
        // TODO: 이메일을 발송하는 로직 구현
        System.out.printf("Sending Email to %s: Subject='%s', Body='%s'%n", to, subject, body);
    }
}
