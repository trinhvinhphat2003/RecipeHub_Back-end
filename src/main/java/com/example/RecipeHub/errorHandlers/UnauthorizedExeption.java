package com.example.RecipeHub.errorHandlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import jakarta.servlet.ServletException;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class UnauthorizedExeption extends RuntimeException{
	public UnauthorizedExeption(String message) {
		super(message);
	}
}
