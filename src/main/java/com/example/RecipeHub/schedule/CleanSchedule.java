package com.example.RecipeHub.schedule;

import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.example.RecipeHub.entities.VerificationToken;
import com.example.RecipeHub.services.JwtService;
import com.example.RecipeHub.services.VerificationTokenService;

@Component
public class CleanSchedule {
	
	private final VerificationTokenService verificationTokenService;
	private final JwtService jwtService;
	
	public CleanSchedule(VerificationTokenService verificationTokenService, JwtService jwtService) {
		super();
		this.verificationTokenService = verificationTokenService;
		this.jwtService = jwtService;
	}

	@Scheduled(cron = "0 0 0 * * 1")
	public void cleanExpiredRegisterToken() {
		List<VerificationToken> verificationTokens = verificationTokenService.getAll();
		for(VerificationToken verificationToken : verificationTokens) {
			if(jwtService.isTokenExpired(verificationToken.getToken())) {
				verificationTokenService.deleteOneById(verificationToken.getId());
			}
		}
	}
}
