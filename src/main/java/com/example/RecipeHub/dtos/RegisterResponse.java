package com.example.RecipeHub.dtos;

import com.example.RecipeHub.enums.RegisterStatusResponse;

public class RegisterResponse {
	private RegisterStatusResponse status;

	public RegisterResponse() {
		super();
	}

	public RegisterResponse(RegisterStatusResponse status) {
		super();
		this.status = status;
	}

	public RegisterStatusResponse getStatus() {
		return status;
	}

	public void setStatus(RegisterStatusResponse status) {
		this.status = status;
	}

	
}
