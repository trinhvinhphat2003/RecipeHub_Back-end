package com.example.RecipeHub.services;

import com.example.RecipeHub.dtos.RegisterRequest;
import com.example.RecipeHub.dtos.RegisterResponse;
import com.example.RecipeHub.dtos.VerificationTokenRequest;

public interface RegisterService {
    RegisterResponse register(RegisterRequest registerRequest, String applicationPath) throws Exception;
    String verifyUser(String token) throws Exception;
}
