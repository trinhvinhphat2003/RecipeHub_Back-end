package com.example.RecipeHub.services;

import com.example.RecipeHub.dtos.RegisterRequest;
import com.example.RecipeHub.dtos.RegisterResponse;
import com.example.RecipeHub.dtos.VerificationTokenRequest;
import com.example.RecipeHub.entities.User;

import jakarta.servlet.http.HttpServletRequest;

public interface RegisterService {
	public final static Integer REGISTER_SUCCESSFULLY = 1;
    public final static Integer EMAIL_DUPLICATED = 1;
    RegisterResponse register(RegisterRequest registerRequest, HttpServletRequest httpServletRequest) throws Exception;
    String verifyUser(String token) throws Exception;
    String createVerificationToken(String userEmail);
    void sendVerificationEmail(RegisterRequest registerRequest, String applicationPath, String token) throws Exception;
}