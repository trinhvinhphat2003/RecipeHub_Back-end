package com.example.RecipeHub.controllers.client.apis;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.RecipeHub.dtos.UserDTO;
import com.example.RecipeHub.entities.FriendshipRequest;
import com.example.RecipeHub.entities.User;
import com.example.RecipeHub.errorHandlers.UnauthorizedExeption;
import com.example.RecipeHub.repositories.FriendshipRepository;
import com.example.RecipeHub.repositories.UserRepository;
import com.example.RecipeHub.services.FriendService;

@RestController
@RequestMapping("/api/v1/")
public class FriendshipController {

	private final FriendshipRepository friendshipRepository;
	private final UserRepository userRepository;
	private final FriendService friendService;

	public FriendshipController(FriendshipRepository friendshipRepository, UserRepository userRepository,
			FriendService friendService) {
		super();
		this.friendshipRepository = friendshipRepository;
		this.userRepository = userRepository;
		this.friendService = friendService;
	}

	@GetMapping("friends")
	public ResponseEntity<ArrayList<UserDTO>> getFriends(@AuthenticationPrincipal User user) {
		if (user == null)
			throw new UnauthorizedExeption("");
		ArrayList<UserDTO> friends = friendService.getAllFriend(user.getUser_id());
		return new ResponseEntity<>(friends, HttpStatus.OK);
	}

	@GetMapping("friends/requests")
	public ResponseEntity<String> getAllRequest(@PathVariable("friend_id") Long friend_id, @AuthenticationPrincipal User user) {
		if (user == null)
			throw new UnauthorizedExeption("");
		
		return new ResponseEntity<String>("", HttpStatus.OK);
	}
	
	@PostMapping("friend/add/{friend_id}")
	public ResponseEntity<String> addFriend(@PathVariable("friend_id") Long friend_id, @AuthenticationPrincipal User user) {
		if (user == null)
			throw new UnauthorizedExeption("");
		
		return new ResponseEntity<String>("", HttpStatus.OK);
	}

	@PostMapping("friend/request/{friend_id}")
	public ResponseEntity<String> requestFriend(@PathVariable("friend_id") Long friend_id, @AuthenticationPrincipal User user) {
		if (user == null)
			throw new UnauthorizedExeption("");
		
		return new ResponseEntity<String>("", HttpStatus.OK);
	}
}
