package com.example.RecipeHub.controllers.client.apis;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.RecipeHub.dtos.LoginDTO;
import com.example.RecipeHub.dtos.ResponseObject;
import com.example.RecipeHub.entities.User;
import com.example.RecipeHub.enums.Role;
import com.example.RecipeHub.errorHandlers.UnauthorizedExeption;
import com.example.RecipeHub.repositories.UserRepository;
import com.example.RecipeHub.services.JwtService;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

	private final AuthenticationManager authenticationManager;

	private final JwtService jwtService;

	private final UserRepository userRepository;

	private final WebClient.Builder webClient;

	public AuthenticationController(AuthenticationManager authenticationManager, JwtService jwtService,
			UserRepository userRepository, WebClient.Builder webClient) {
		super();
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
		this.userRepository = userRepository;
		this.webClient = webClient;
	}

	@PostMapping("/basic/login")
	public ResponseEntity<String> handleBasicLogin(@RequestBody LoginDTO loginDTO) {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
		UserDetails userDetails = userRepository.findByEmail(loginDTO.getEmail()).get();
		String JwtToken = jwtService.generateToken(userDetails);
		return new ResponseEntity<String>("Bearer " + JwtToken, HttpStatus.OK);
	}

	@PostMapping("/google/oauth/login/{token}")
	public ResponseEntity<String> handleOauthLogin(@PathVariable("token") String googleToken) {
		String url = "https://www.googleapis.com/oauth2/v3/tokeninfo?id_token=" + googleToken;
		final String[] emailRes = new String[1];
		WebClient.Builder builder = WebClient.builder();
		ResponseObject responseObject;
		try {
			responseObject = webClient.build().get().uri(url).retrieve().bodyToMono(ResponseObject.class)
					.block();
		} catch (Exception e) {
			throw new UnauthorizedExeption("");
		}
		String email = responseObject.getEmail();
		Optional<User> user = userRepository.findByEmail(email);
		if (!user.isPresent()) {
			User newUser = new User(email, "", Role.USER);
			userRepository.save(newUser);
			emailRes[0] = email;
			user = userRepository.findByEmail(email);
		}
		String JwtToken = jwtService.generateToken(user.get());
		return new ResponseEntity<String>("Bearer " + JwtToken, HttpStatus.OK);

	}
}
