package com.example.RecipeHub.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.RecipeHub.entities.VerificationToken;
import com.example.RecipeHub.repositories.VerificationTokenRepository;
import com.example.RecipeHub.services.VerificationTokenService;

@Service
public class VerificationTokenServiceImpl implements VerificationTokenService{

	private final VerificationTokenRepository verificationTokenRepository;
	
	public VerificationTokenServiceImpl(VerificationTokenRepository verificationTokenRepository) {
		super();
		this.verificationTokenRepository = verificationTokenRepository;
	}

	@Override
	public List<VerificationToken> getAll() {
		return verificationTokenRepository.findAll();
	}

	@Override
	public void deleteOneById(Long id) {
		verificationTokenRepository.deleteById(id);
		
	}
	
}
