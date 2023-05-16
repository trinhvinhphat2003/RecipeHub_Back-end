package com.example.RecipeHub.controllers.client.apis;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthenticationController {
	@PostMapping("login")
	public ResponseEntity<String> handleLogin() {
		return new ResponseEntity<String>("JWT code", HttpStatus.OK);
	}
}
