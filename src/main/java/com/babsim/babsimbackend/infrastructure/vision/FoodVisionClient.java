package com.babsim.babsimbackend.infrastructure.vision;

import java.util.List;

public interface FoodVisionClient {
    List<String> analyzeImage(String imageUrl);
}
