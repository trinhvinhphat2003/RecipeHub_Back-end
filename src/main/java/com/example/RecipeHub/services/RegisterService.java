package com.example.RecipeHub.services;

import com.example.RecipeHub.client.dtos.request.RegisterRequest;
import com.example.RecipeHub.client.dtos.response.RegisterResponse;
import jakarta.servlet.http.HttpServletRequest;

public interface RegisterService {
    RegisterResponse register(RegisterRequest registerRequest, HttpServletRequest httpServletRequest) throws Exception;
    String verifyUser(String token) throws Exception;
    String createVerificationToken(String userEmail);
}
