package com.example.RecipeHub.services;

import com.example.RecipeHub.dtos.RegisterRequest;
import com.example.RecipeHub.dtos.RegisterResponse;

public interface RegisterService {
    RegisterResponse register(RegisterRequest registerRequest) throws Exception;
    String verifyUser(String token) throws Exception;
    String createVerificationToken(String userEmail);
    void sendVerificationEmail(RegisterRequest registerRequest, String applicationPath, String token) throws Exception;
}
