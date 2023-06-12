package com.example.RecipeHub.dtos;

public class RegisterResponse {
    private String jwtToken;

	public RegisterResponse() {
		super();
	}

	public RegisterResponse(String jwtToken) {
		super();
		this.jwtToken = jwtToken;
	}

	public String getJwtToken() {
		return jwtToken;
	}

	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}
    
}
