package com.example.RecipeHub.dtos;

public class ForgotPasswordVerifiedMailDTO {
	private String fullName;
	private String email;
	private String token;
	public ForgotPasswordVerifiedMailDTO(String fullName, String email, String token) {
		super();
		this.fullName = fullName;
		this.email = email;
		this.token = token;
	}
	public ForgotPasswordVerifiedMailDTO() {
		super();
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	
}
