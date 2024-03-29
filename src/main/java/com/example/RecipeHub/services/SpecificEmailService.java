package com.example.RecipeHub.services;

import com.example.RecipeHub.dtos.ForgotPasswordVerifiedMailDTO;
import com.example.RecipeHub.dtos.ForgottenPasswordDto;
import com.example.RecipeHub.entities.User;

public interface SpecificEmailService {
    void sendNewPasswordEmailInform(ForgottenPasswordDto request) throws Exception;
    void sendPasswordChangeReminder(User user) throws Exception;
    void sendForgotPasswordVerifiedEmail(ForgotPasswordVerifiedMailDTO info) throws Exception;
}