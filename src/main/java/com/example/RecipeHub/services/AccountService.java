package com.example.RecipeHub.services;

import com.example.RecipeHub.dtos.ForgottenPasswordDto;
import com.example.RecipeHub.entities.User;
import com.example.RecipeHub.eventListeners.events.ForgotPasswordEvent;
import com.example.RecipeHub.eventListeners.events.ForgotPasswordVerifiedMailEvent;

public interface  AccountService {
	void changePassword(String newPassword, User user);
    ForgottenPasswordDto generatePassword(ForgottenPasswordDto request) throws Exception;
    ForgotPasswordEvent verifyForgotPasswordToken(String token) throws Exception;
	ForgotPasswordVerifiedMailEvent generateVerifyTokenEmailEvent(ForgottenPasswordDto forgottenPasswordDto);
}
