package com.babsim.babsimbackend.infrastructure.s3;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Component
@Profile("!prod") // prod 프로필이 아닐 때만 활성화
public class MockS3Uploader implements S3Uploader {

    @Override
    public String upload(MultipartFile file, String dirName) throws IOException {
        // AI-Refactor: 실제 S3에 업로드하는 대신, 가상의 URL을 생성하여 반환합니다.
        String fileName = dirName + "/" + UUID.randomUUID() + "-" + file.getOriginalFilename();
        return "https://s3.example.com/babsim-bucket/" + fileName;
    }
}
