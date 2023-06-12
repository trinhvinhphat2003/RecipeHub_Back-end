package com.example.RecipeHub.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
public class LoginResponseDTO {
	private String JwtToken;
	private UserDTO user;
	public LoginResponseDTO(String jwtToken, UserDTO user) {
		super();
		JwtToken = jwtToken;
		this.user = user;
	}
	public String getJwtToken() {
		return JwtToken;
	}
	public void setJwtToken(String jwtToken) {
		JwtToken = jwtToken;
	}
	public UserDTO getUser() {
		return user;
	}
	public void setUser(UserDTO user) {
		this.user = user;
	}
	public LoginResponseDTO() {
		super();
	}
	
	
}
