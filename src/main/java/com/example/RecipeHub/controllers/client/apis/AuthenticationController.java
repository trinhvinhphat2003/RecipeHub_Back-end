package com.example.RecipeHub.controllers.client.apis;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.RecipeHub.dtos.LoginDTO;
import com.example.RecipeHub.dtos.LoginResponseDTO;
import com.example.RecipeHub.dtos.RegisterRequest;
import com.example.RecipeHub.dtos.RegisterResponse;
import com.example.RecipeHub.enums.RegisterStatusResponse;
import com.example.RecipeHub.eventListeners.events.RegistrationCompletionEvent;
import com.example.RecipeHub.mappers.UserMapper;
import com.example.RecipeHub.services.AuthenticateService;
import com.example.RecipeHub.services.RegisterService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthenticationController {

	private final AuthenticateService authenticateService;
	private final RegisterService accountService;
	private final ApplicationEventPublisher eventPublisher;
	
	
	public AuthenticationController(AuthenticateService authenticateService, RegisterService accountService, ApplicationEventPublisher eventPublisher) {
		super();
		this.authenticateService = authenticateService;
		this.accountService = accountService;
		this.eventPublisher = eventPublisher;
	}

	@PostMapping("/basic/login")
	public ResponseEntity<LoginResponseDTO> handleBasicLogin(@Valid @RequestBody LoginDTO loginDTO) {
		return new ResponseEntity<>(authenticateService.authenticateBasic(loginDTO), HttpStatus.OK);
	}

	@PostMapping("/google/oauth/login/{token}")
	public ResponseEntity<LoginResponseDTO> handleOauthLogin(@PathVariable("token") String googleToken) {
		return new ResponseEntity<>(authenticateService.authenticateOauth(googleToken), HttpStatus.OK);

	}
	
	@PostMapping(path = "/register")
	public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest registerRequest, HttpServletRequest request) throws Exception {
		String token = accountService.register(registerRequest, request);
		eventPublisher.publishEvent(new RegistrationCompletionEvent(registerRequest, getApplicationPath(request), token));
		return ResponseEntity.ok(RegisterStatusResponse.REGISTER_SUCCESSFULLY.name());
	}
	
	@GetMapping(path = "/verify-user")
	public ResponseEntity<String> verifyUser(@RequestParam(name = "token") String verificationToken) throws Exception{
		return ResponseEntity.ok(accountService.verifyUser(verificationToken));
	}
	
	private String getApplicationPath(HttpServletRequest request){
		return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	}
}
