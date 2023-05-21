package com.example.RecipeHub.errorHandlers;

public class BadRequestExeption extends RuntimeException{
	public BadRequestExeption(String message) {
		super(message);
	}
}
