package com.example.RecipeHub.services;

import com.example.RecipeHub.dtos.request.RegisterRequest;
import com.example.RecipeHub.dtos.response.RegisterResponse;

public interface RegisterService {
    RegisterResponse register(RegisterRequest registerRequest) throws Exception;
    String verifyUser(String token) throws Exception;
    String createVerificationToken(String userEmail);
}
