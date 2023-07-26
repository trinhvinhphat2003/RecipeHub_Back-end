package com.example.RecipeHub.controllers.client.apis;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.RecipeHub.dtos.EditProfileRequest;
import com.example.RecipeHub.dtos.UserDTO;
import com.example.RecipeHub.entities.User;
import com.example.RecipeHub.enums.Role;
import com.example.RecipeHub.errorHandlers.NotFoundExeption;
import com.example.RecipeHub.mappers.UserMapper;
import com.example.RecipeHub.services.UserService;


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
	
	@GetMapping("/global/user/profile/{userId}")
	public ResponseEntity<UserDTO> getGlobalUser(@AuthenticationPrincipal User user, @PathVariable("userId") Long userId) {
		UserDTO result = UserMapper.INSTANCE.userToUserDTO(userService.getUserById(userId));
		if(result.getRole().equals(Role.ADMIN.name())) throw new NotFoundExeption("this user is not existed");
		return ResponseEntity.ok(result);
	}
}
