package com.babsim.babsimbackend.infrastructure.storage;

import org.springframework.web.multipart.MultipartFile;

// AI 생성: 파일 저장 서비스 인터페이스
public interface FileStorageService {

    /**
     * 파일을 저장하고 접근 가능한 URL을 반환합니다.
     * @param file 저장할 파일
     * @return 저장된 파일의 URL
     */
    String storeFile(MultipartFile file);
}
