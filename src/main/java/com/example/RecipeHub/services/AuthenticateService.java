package com.example.RecipeHub.services;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.RecipeHub.dtos.LoginDTO;
import com.example.RecipeHub.dtos.LoginResponseDTO;
import com.example.RecipeHub.dtos.ResponseObject;
import com.example.RecipeHub.entities.User;
import com.example.RecipeHub.enums.Gender;
import com.example.RecipeHub.enums.Role;
import com.example.RecipeHub.errorHandlers.UnauthorizedExeption;
import com.example.RecipeHub.mappers.UserMapper;
import com.example.RecipeHub.repositories.UserRepository;

@Service
public class AuthenticateService {
	
	private final AuthenticationManager authenticationManager;

	private final JwtService jwtService;

	private final UserRepository userRepository;

	private final WebClient.Builder webClient;

	public AuthenticateService(AuthenticationManager authenticationManager, JwtService jwtService,
			UserRepository userRepository, WebClient.Builder webClient) {
		super();
		this.authenticationManager = authenticationManager;
		this.jwtService = jwtService;
		this.userRepository = userRepository;
		this.webClient = webClient;
	}
	
	public LoginResponseDTO authenticateBasic(LoginDTO loginDTO) {
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));
		User user = userRepository.findByEmail(loginDTO.getEmail()).get();
		String JwtToken = "Bearer " + jwtService.generateToken(user);
		LoginResponseDTO responseDto = new LoginResponseDTO(JwtToken, UserMapper.INSTANCE.userToUserDTO(user));
		return responseDto;
	}
	
	public LoginResponseDTO authenticateOauth(String googleToken) {
		String url = "https://www.googleapis.com/oauth2/v3/tokeninfo?id_token=" + googleToken;
		final String[] emailRes = new String[1];
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
			User newUser = new User(email, "", Role.USER, responseObject.getName(), Gender.UNKNOW);
			userRepository.save(newUser);
			emailRes[0] = email;
			user = userRepository.findByEmail(email);
		}
		String JwtToken = "Bearer " + jwtService.generateToken(user.get());
		LoginResponseDTO responseDto = new LoginResponseDTO(JwtToken, UserMapper.INSTANCE.userToUserDTO(user.get()));
		
		return responseDto;
	}
}
