package com.example.RecipeHub.controllers.client.apis;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.RecipeHub.dtos.LoginDTO;
import com.example.RecipeHub.services.AuthenticateService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

	private final AuthenticateService authenticateService;
	
	public AuthenticationController(AuthenticateService authenticateService) {
		super();
		this.authenticateService = authenticateService;
	}

	@PostMapping("/basic/login")
	public ResponseEntity<String> handleBasicLogin(@RequestBody LoginDTO loginDTO) {
		String JwtToken = authenticateService.authenticateBasic(loginDTO);
		return new ResponseEntity<String>("Bearer " + JwtToken, HttpStatus.OK);
	}

	@PostMapping("/google/oauth/login/{token}")
	public ResponseEntity<String> handleOauthLogin(@PathVariable("token") String googleToken) {
		String JwtToken = authenticateService.authenticationOauth(googleToken);
		return new ResponseEntity<String>("Bearer " + JwtToken, HttpStatus.OK);

	}
}
