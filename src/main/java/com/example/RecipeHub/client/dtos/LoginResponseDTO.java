package com.example.RecipeHub.client.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
public class LoginResponseDTO {
	private Integer status;
	private String JwtToken;
	private UserDTO user;
	public LoginResponseDTO(String jwtToken, UserDTO user, Integer status) {
		super();
		JwtToken = jwtToken;
		this.user = user;
		this.status = status;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
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
