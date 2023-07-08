package com.example.RecipeHub.services;

import com.example.RecipeHub.client.dtos.ForgottenPasswordDto;
import com.example.RecipeHub.entities.User;

public interface SpecificEmailService {
    void sendNewPasswordEmailInform(ForgottenPasswordDto request) throws Exception;
    void sendPasswordChangeReminder(User user) throws Exception;
}