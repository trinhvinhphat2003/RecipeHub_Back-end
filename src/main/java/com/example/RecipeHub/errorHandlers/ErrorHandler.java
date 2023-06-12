package com.example.RecipeHub.errorHandlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ErrorHandler {
	@ExceptionHandler(UnauthorizedExeption.class)
	public ResponseEntity<String> accessDenied(UnauthorizedExeption ex){
		return new ResponseEntity<>(ex.getMessage() ,HttpStatus.UNAUTHORIZED);
	}
	@ExceptionHandler(UserNotFoundExeption.class)
	public ResponseEntity<String> accessDenied(UserNotFoundExeption ex){
		return new ResponseEntity<>(ex.getMessage() ,HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(NotFoundExeption.class)
	public ResponseEntity<String> accessDenied(NotFoundExeption ex){
		return new ResponseEntity<>(ex.getMessage() ,HttpStatus.NOT_FOUND);
	}
	@ExceptionHandler(BadRequestExeption.class)
	public ResponseEntity<String> accessDenied(BadRequestExeption ex){
		return new ResponseEntity<>(ex.getMessage() ,HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(ForbiddenExeption.class)
	public ResponseEntity<String> accessDenied(ForbiddenExeption ex){
		return new ResponseEntity<>(ex.getMessage() ,HttpStatus.FORBIDDEN);
	}
}
