package com.example.RecipeHub.errorHandlers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
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
	@ExceptionHandler(InternalExeption.class)
	public ResponseEntity<String> accessDenied(InternalExeption ex){
		return new ResponseEntity<>(ex.getMessage() ,HttpStatus.INTERNAL_SERVER_ERROR);
	}
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<String> dtoValidationExeption(MethodArgumentNotValidException ex) {
		StringBuilder errorMessage = new StringBuilder();
		List<FieldError> fieldErrors = ex.getBindingResult().getFieldErrors();
		for(FieldError error : fieldErrors) {
			errorMessage.append(error.getField()).append(":").append(error.getDefaultMessage()).append("\n");
		}
		return ResponseEntity.badRequest().body(errorMessage.toString());
	}
}
