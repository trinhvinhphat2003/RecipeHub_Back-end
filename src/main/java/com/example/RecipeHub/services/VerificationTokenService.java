package com.example.RecipeHub.services;

import java.util.List;

import com.example.RecipeHub.entities.VerificationToken;

public interface VerificationTokenService {
	public List<VerificationToken> getAll();
	public void deleteOneById(Long id);
}
