package com.example.RecipeHub.services;

import com.example.RecipeHub.client.dtos.ForgottenPasswordDto;
import com.example.RecipeHub.entities.User;

public interface  AccountService {
	void changePassword(String newPassword, User user);
    ForgottenPasswordDto generatePassword(ForgottenPasswordDto request) throws Exception;
}
