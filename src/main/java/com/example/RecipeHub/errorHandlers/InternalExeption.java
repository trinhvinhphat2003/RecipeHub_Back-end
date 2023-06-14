package com.example.RecipeHub.errorHandlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalExeption extends RuntimeException{
	public InternalExeption(String message) {
		super(message);
	} 
}
