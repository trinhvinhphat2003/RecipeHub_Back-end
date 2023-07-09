package com.example.RecipeHub.eventListeners.events;

import org.springframework.context.ApplicationEvent;

import com.example.RecipeHub.dtos.ForgotPasswordVerifiedMailDTO;

public class ForgotPasswordVerifiedMailEvent extends ApplicationEvent{
	
	private ForgotPasswordVerifiedMailDTO forgotPasswordVerifiedMailDTO;
	
	public ForgotPasswordVerifiedMailEvent(ForgotPasswordVerifiedMailDTO forgotPasswordVerifiedMailDTO) {
		super(forgotPasswordVerifiedMailDTO);
		this.forgotPasswordVerifiedMailDTO = forgotPasswordVerifiedMailDTO;
	}

	public ForgotPasswordVerifiedMailDTO getForgotPasswordVerifiedMailDTO() {
		return forgotPasswordVerifiedMailDTO;
	}

	public void setForgotPasswordVerifiedMailDTO(ForgotPasswordVerifiedMailDTO forgotPasswordVerifiedMailDTO) {
		this.forgotPasswordVerifiedMailDTO = forgotPasswordVerifiedMailDTO;
	}
	
}
