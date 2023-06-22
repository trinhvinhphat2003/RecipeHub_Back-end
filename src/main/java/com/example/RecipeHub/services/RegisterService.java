package com.example.RecipeHub.services;

import com.example.RecipeHub.client.dtos.RegisterRequest;
import com.example.RecipeHub.client.dtos.RegisterResponse;
import com.example.RecipeHub.client.dtos.VerificationTokenRequest;
import com.example.RecipeHub.entities.User;

import jakarta.servlet.http.HttpServletRequest;

public interface RegisterService {
    RegisterResponse register(RegisterRequest registerRequest, HttpServletRequest httpServletRequest) throws Exception;
    String verifyUser(String token) throws Exception;
    String createVerificationToken(String userEmail);
    void sendVerificationEmail(RegisterRequest registerRequest, String applicationPath, String token) throws Exception;
}