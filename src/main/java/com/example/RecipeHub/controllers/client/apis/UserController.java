package com.example.RecipeHub.controllers.client.apis;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.RecipeHub.client.dtos.EditProfileRequest;
import com.example.RecipeHub.client.dtos.UserDTO;
import com.example.RecipeHub.entities.User;
import com.example.RecipeHub.mappers.UserMapper;
import com.example.RecipeHub.services.UserService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

@RestController
@RequestMapping("/api/v1")
public class UserController {
	
	private final UserService userService;
	
	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}

	@PutMapping("/user/edit-profile")
	public ResponseEntity<String> editProfile(@AuthenticationPrincipal User user, @RequestBody EditProfileRequest request) {
		userService.editProfile(request, user.getUserId());
		return ResponseEntity.ok("profile is edited successfully");
	}
	
	@GetMapping("/user/profile")
	public ResponseEntity<UserDTO> getUser(@AuthenticationPrincipal User user) {
		UserDTO result = UserMapper.INSTANCE.userToUserDTO(userService.getUserById(user.getUserId()));
		return ResponseEntity.ok(result);
	}
}
