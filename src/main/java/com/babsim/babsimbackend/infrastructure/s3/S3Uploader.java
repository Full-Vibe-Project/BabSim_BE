package com.babsim.babsimbackend.infrastructure.s3;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface S3Uploader {
    String upload(MultipartFile file, String dirName) throws IOException;
}
