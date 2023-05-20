package com.example.RecipeHub.controllers.client.apis;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.RecipeHub.dtos.FriendDTO;
import com.example.RecipeHub.entities.Friendship;
import com.example.RecipeHub.entities.User;
import com.example.RecipeHub.repositories.FriendshipRepository;
import com.example.RecipeHub.repositories.UserRepository;

@RestController
@RequestMapping("/api/v1/")
public class FriendshipController {
	
	private final FriendshipRepository friendshipRepository;
	private final UserRepository userRepository;
	
	public FriendshipController(FriendshipRepository friendshipRepository, UserRepository userRepository) {
		super();
		this.friendshipRepository = friendshipRepository;
		this.userRepository = userRepository;
	}

	@GetMapping("friends")
	public ResponseEntity<ArrayList<FriendDTO>> getFriends() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		User user = (User)auth.getPrincipal();
		ArrayList<FriendDTO> dtos = new ArrayList<FriendDTO>();
		return new ResponseEntity<>(dtos, HttpStatus.OK);
	}
}
