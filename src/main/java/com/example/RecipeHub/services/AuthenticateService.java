package com.example.RecipeHub.services;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.RecipeHub.client.dtos.LoginDTO;
import com.example.RecipeHub.client.dtos.LoginResponseDTO;
import com.example.RecipeHub.client.dtos.ResponseObject;
import com.example.RecipeHub.entities.User;
import com.example.RecipeHub.enums.Gender;
import com.example.RecipeHub.enums.LoginType;
import com.example.RecipeHub.enums.Role;
import com.example.RecipeHub.errorHandlers.ForbiddenExeption;
import com.example.RecipeHub.errorHandlers.UnauthorizedExeption;
import com.example.RecipeHub.mappers.UserMapper;
import com.example.RecipeHub.repositories.UserRepository;
import com.example.RecipeHub.utils.DateTimeUtil;

@Service
public class AuthenticateService {
	
	private final AuthenticationManager authenticationManager;

	private final JwtService jwtService;

	private final UserRepository userRepository;
	
	private final Integer LOGIN_SUCCESSFULLY = 1;
	private final Integer ACCOUNT_IS_BLOCKED = 2;
	private final Integer ACCOUNT_IS_NOT_VERIFIED = 3;

	//this is used to make a http request to google
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
		LoginResponseDTO responseDto = null;
		//check if user exist or not
		authenticationManager
				.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getPassword()));

		//get user
		User user = userRepository.findByEmail(loginDTO.getEmail()).get();
		
		if(!user.isEnable()) {
			responseDto = new LoginResponseDTO("", UserMapper.INSTANCE.userToUserDTO(user), ACCOUNT_IS_NOT_VERIFIED);
			return responseDto;
		} else if(user.isBlocked()) {
			responseDto = new LoginResponseDTO("", UserMapper.INSTANCE.userToUserDTO(user), ACCOUNT_IS_BLOCKED);
			return responseDto;
		} else {
			//generate jwt token
			String JwtToken = "Bearer " + jwtService.generateToken(user);
			responseDto = new LoginResponseDTO(JwtToken, UserMapper.INSTANCE.userToUserDTO(user), LOGIN_SUCCESSFULLY);
			return responseDto;
		}
	}
	
	public LoginResponseDTO authenticateOauth(String googleToken) {
		//merge google url with token
		String url = "https://www.googleapis.com/oauth2/v1/userinfo?access_token=" + googleToken;
		
		
		final String[] emailRes = new String[1];
		
		//object obtain google's response
		ResponseObject responseObject;
		try {
			responseObject = webClient.build().get().uri(url).retrieve().bodyToMono(ResponseObject.class)
					.block();
		} catch (Exception e) {
			throw new UnauthorizedExeption("");
		}
		
		//get email from google's response and check if exist
		String email = responseObject.getEmail();
		Optional<User> user = userRepository.findByEmail(email);
		//if user not exist, create a new
		if (!user.isPresent()) {
			User newUser = new User(email, "", Role.USER, responseObject.getName(), Gender.UNKNOW, true, DateTimeUtil.milisecondToDate(System.currentTimeMillis()), responseObject.getPicture(), LoginType.GOOGLE, false);
			//User newUser2 = new User(null, email, "", responseObject.getName(), responseObject.getPicture(), responseObject.getBirthDay(), Role.USER, Gender.MALE, true, null, null, null);
			userRepository.save(newUser);
			emailRes[0] = email;
			user = userRepository.findByEmail(email);
		}
		//generate jwt token
		String JwtToken = "Bearer " + jwtService.generateToken(user.get());
		
		//response
		LoginResponseDTO responseDto = new LoginResponseDTO(JwtToken, UserMapper.INSTANCE.userToUserDTO(user.get()), LOGIN_SUCCESSFULLY);
		
		return responseDto;
	}
}
