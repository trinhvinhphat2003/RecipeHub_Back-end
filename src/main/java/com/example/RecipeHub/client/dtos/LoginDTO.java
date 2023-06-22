package com.example.RecipeHub.client.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
public class LoginDTO {
	private String email;
	private String password;
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
	public LoginDTO(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}
	public LoginDTO() {
		super();
	}
	
	
}
