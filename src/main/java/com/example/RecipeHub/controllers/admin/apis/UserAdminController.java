package com.example.RecipeHub.controllers.admin.apis;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.RecipeHub.dtos.RecipesPaginationResponse;
import com.example.RecipeHub.dtos.UserDTO;
import com.example.RecipeHub.dtos.UsersPaginationResponse;
import com.example.RecipeHub.entities.User;
import com.example.RecipeHub.services.UserService;

@Controller
@RequestMapping("/api/v1/admin")
public class UserAdminController {
	
	private final UserService userService;
	
	public UserAdminController(UserService userService) {
		super();
		this.userService = userService;
	}

	@GetMapping("/users")
	public ResponseEntity<UsersPaginationResponse> getAllRecipe(@AuthenticationPrincipal User user,
			@RequestParam(value = "page", defaultValue = "0", required = false) int page,
			@RequestParam(value = "size", defaultValue = "20", required = false) int size,
			@RequestParam(value = "query", defaultValue = "", required = false) String query,
			@RequestParam(value = "isBlocked", required = false) Integer isBlocked,
			@RequestParam(value = "sort", defaultValue = "user_id", required = false) String sort,
			@RequestParam(value = "direction", defaultValue = "desc", required = false) String direction) { 
		UsersPaginationResponse response = new UsersPaginationResponse(userService.filterUserAndPagination(page, size, sort, direction, query, isBlocked), userService.countOfFilterUser(sort, direction, query, isBlocked));
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping("/user/block/{user_id}")
	public ResponseEntity<String> blockUser(@AuthenticationPrincipal User user, @PathVariable("user_id") Long userId) {
		userService.blockUserByUserId(userId);
		return ResponseEntity.ok("this user have been blocked");
	}
	
	@PostMapping("/user/unblock/{user_id}")
	public ResponseEntity<String> unBlockUser(@AuthenticationPrincipal User user, @PathVariable("user_id") Long userId) {
		userService.unBlockUserByUserId(userId);
		return ResponseEntity.ok("this user have been unblocked");
	}
	
	@GetMapping("/user/total")
	public ResponseEntity<Integer> countRecipeCurrentInDB() {
		return ResponseEntity.ok(userService.countUserCurrentInDB());
	}
}
