package com.example.RecipeHub.eventListeners.listenners;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.example.RecipeHub.eventListeners.events.ForgotPasswordVerifiedMailEvent;
import com.example.RecipeHub.services.SpecificEmailService;

@Component
public class ForgotPasswordVerifiedMailListenner {
	
	private final SpecificEmailService emailService;
	
	public ForgotPasswordVerifiedMailListenner(SpecificEmailService emailService) {
		super();
		this.emailService = emailService;
	}

	@EventListener
	public void handleSendForgotPasswordVerifiedMail(ForgotPasswordVerifiedMailEvent event) throws Exception {
		emailService.sendForgotPasswordVerifiedEmail(event.getForgotPasswordVerifiedMailDTO());
	}
}
