package com.example.RecipeHub.errorHandlers;

import jakarta.servlet.ServletException;

public class UnauthorizedExeption extends RuntimeException{
	public UnauthorizedExeption(String message) {
		super(message);
	}
}
