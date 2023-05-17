package com.example.RecipeHub.controllers.client.apis;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.RecipeHub.dtos.LoginDTO;
import com.example.RecipeHub.repositories.UserRepository;
import com.example.RecipeHub.services.JwtService;

@RestController
@RequestMapping("/api/v1/auth/")
public class AuthenticationController {
	
	private final AuthenticationManager authenticationManager;
	
	private final JwtService jwtService;
	
	private final UserRepository userRepository;
	
	public AuthenticationController(AuthenticationManager authenticationManager, JwtService jwtService,
			UserRepository userRepository) {
		super();
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
		this.userRepository = userRepository;
	}

	@PostMapping("login")
	public ResponseEntity<String> handleLogin(@RequestBody LoginDTO loginDTO) {
		//authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
		UserDetails userDetails = userRepository.findByEmail(loginDTO.getEmail()).get();
		String JwtToken = jwtService.generateToken(userDetails);
		return new ResponseEntity<String>("Bearer " + JwtToken, HttpStatus.OK);
	}
}
