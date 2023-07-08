package com.example.RecipeHub.client.dtos;

public class RegisterResponse {
	private Integer status;

	public RegisterResponse() {
		super();
	}

	public RegisterResponse(Integer status) {
		super();
		this.status = status;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}
