package com.example.RecipeHub.dtos;

public class VerificationTokenRequest {
    private UserDTO userDTO;
    private String token;
	public VerificationTokenRequest() {
		super();
	}
	public VerificationTokenRequest(UserDTO userDTO, String token) {
		super();
		this.userDTO = userDTO;
		this.token = token;
	}
	public UserDTO getUserDTO() {
		return userDTO;
	}
	public void setUserDTO(UserDTO userDTO) {
		this.userDTO = userDTO;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
    
    
}