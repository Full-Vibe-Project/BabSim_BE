package com.babsim.babsimbackend.infrastructure.storage.impl;

import com.babsim.babsimbackend.infrastructure.storage.FileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

// AI 생성: S3 파일 저장 구현체
@Service("s3FileStorageService") // Bean 이름 지정
public class S3FileStorageImpl implements FileStorageService {

    // private final AmazonS3 amazonS3;
    // private final String bucketName;

    @Override
    public String storeFile(MultipartFile file) {
        // TODO: 파일을 AWS S3에 업로드하고 파일 URL을 반환하는 로직 구현
        System.out.println("Uploading file to S3: " + file.getOriginalFilename());
        return "https://s3.amazonaws.com/your-bucket/" + file.getOriginalFilename();
    }
}
