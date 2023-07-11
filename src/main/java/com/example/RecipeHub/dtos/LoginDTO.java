package com.example.RecipeHub.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
public class LoginDTO {
	@NotBlank(message = "Email is required")
	@Email(message = "Email is not valid")
	private String email;
	@NotBlank(message = "Password is required")
	@Size(min = 6, message = "Password must have at least 6 charactor")
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
