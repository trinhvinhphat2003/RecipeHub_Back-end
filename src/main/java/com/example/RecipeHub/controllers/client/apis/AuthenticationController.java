package com.example.RecipeHub.controllers.client.apis;

import com.example.RecipeHub.dtos.RegisterRequest;
import com.example.RecipeHub.dtos.RegisterResponse;
import com.example.RecipeHub.eventListeners.RegistrationCompletionEvent;
import com.example.RecipeHub.services.RegisterService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.RecipeHub.dtos.LoginDTO;
import com.example.RecipeHub.services.AuthenticateService;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

	private final AuthenticateService authenticateService;
	private final RegisterService accountService;
	private final ApplicationEventPublisher eventPublisher;

	@PostMapping("/basic/login")
	public ResponseEntity<String> handleBasicLogin(@RequestBody LoginDTO loginDTO) {
		String JwtToken = authenticateService.authenticateBasic(loginDTO);
		return new ResponseEntity<>("Bearer " + JwtToken, HttpStatus.OK);
	}

	@PostMapping("/google/oauth/login/{token}")
	public ResponseEntity<String> handleOauthLogin(@PathVariable("token") String googleToken) {
		String JwtToken = authenticateService.authenticationOauth(googleToken);
		return new ResponseEntity<>("Bearer " + JwtToken, HttpStatus.OK);

	}

	@PostMapping(path = "/register")
	public ResponseEntity<RegisterResponse> register(@RequestBody RegisterRequest registerRequest, HttpServletRequest request) throws Exception {
		RegisterResponse registerResponse = accountService.register(registerRequest);
		eventPublisher.publishEvent(new RegistrationCompletionEvent(registerRequest, getApplicationPath(request)));
		return ResponseEntity.ok(registerResponse);
	}

	@GetMapping(path = "/verify-user")
	public ResponseEntity<String> verifyUser(@RequestParam(name = "token") String verificationToken) throws Exception{
		return ResponseEntity.ok(accountService.verifyUser(verificationToken));
	}

	private String getApplicationPath(HttpServletRequest request){
		return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	}
}
