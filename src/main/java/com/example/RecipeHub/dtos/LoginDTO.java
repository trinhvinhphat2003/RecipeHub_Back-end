package com.example.RecipeHub.dtos;

public class LoginDTO {
	private String email;
	private String password;
	//constructors
	public LoginDTO(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}
	//getters, setters
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public LoginDTO() {
		super();
	}
	
}
