package com.babsim.babsimbackend.infrastructure.storage.impl;

import com.babsim.babsimbackend.infrastructure.storage.FileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

// AI 생성: 로컬 파일 저장 구현체
@Service("localFileStorageService") // Bean 이름 지정
public class LocalFileStorageImpl implements FileStorageService {

    @Override
    public String storeFile(MultipartFile file) {
        // TODO: 파일을 로컬 시스템에 저장하고 파일 경로를 반환하는 로직 구현
        System.out.println("Storing file locally: " + file.getOriginalFilename());
        return "/path/to/local/" + file.getOriginalFilename();
    }
}
