package com.example.RecipeHub.services;

import com.example.RecipeHub.dtos.LoginDTO;
import com.example.RecipeHub.dtos.LoginResponseDTO;

public interface AuthenticateService {
	public LoginResponseDTO authenticateBasic(LoginDTO loginDTO);
	public LoginResponseDTO authenticateOauth(String googleToken);
}
