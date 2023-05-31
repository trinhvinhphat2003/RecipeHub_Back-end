package com.example.RecipeHub.errorHandlers;

public class NotFoundExeption extends RuntimeException{
	public NotFoundExeption(String message) {
		super(message);
	}
}
