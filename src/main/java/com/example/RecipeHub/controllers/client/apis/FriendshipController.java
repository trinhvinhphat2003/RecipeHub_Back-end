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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.RecipeHub.dtos.FriendshipRequestDTO;
import com.example.RecipeHub.dtos.UserDTO;
import com.example.RecipeHub.entities.FriendshipRequest;
import com.example.RecipeHub.entities.User;
import com.example.RecipeHub.enums.Friendship_status;
import com.example.RecipeHub.errorHandlers.BadRequestExeption;
import com.example.RecipeHub.errorHandlers.UnauthorizedExeption;
import com.example.RecipeHub.repositories.FriendshipRepository;
import com.example.RecipeHub.repositories.UserRepository;
import com.example.RecipeHub.services.FriendService;
import com.example.RecipeHub.services.FriendshipRequestService;
import com.example.RecipeHub.services.UserService;

@RestController
@RequestMapping("/api/v1/")
public class FriendshipController {

	private final FriendshipRepository friendshipRepository;
	private final UserService userService;
	private final FriendService friendService;
	private final FriendshipRequestService friendshipRequestService;

	public FriendshipController(FriendshipRepository friendshipRepository, UserService userService,
			FriendService friendService, FriendshipRequestService friendshipRequestService) {
		super();
		this.friendshipRepository = friendshipRepository;
		this.userService = userService;
		this.friendService = friendService;
		this.friendshipRequestService = friendshipRequestService;
	}

	@GetMapping("friends")
	public ResponseEntity<ArrayList<UserDTO>> getFriends(@AuthenticationPrincipal User user) {
		if (user == null)
			throw new UnauthorizedExeption("");
		ArrayList<UserDTO> friends = friendService.getAllFriend(user.getUser_id());
		return new ResponseEntity<>(friends, HttpStatus.OK);
	}

	@PostMapping("friend/request/{receiver_id}")
	public ResponseEntity<String> getAllRequest(@PathVariable("receiver_id") Long receiver_id, @AuthenticationPrincipal User user) {
		if (user == null)
			throw new UnauthorizedExeption("");
		User receiver = userService.getUserById(receiver_id);
		FriendshipRequest friendshipRequest = new FriendshipRequest(user, receiver, Friendship_status.WAITING);
		friendshipRequestService.save(friendshipRequest);
		return new ResponseEntity<String>("you have send friend's request to " + receiver.getFull_name(), HttpStatus.OK);
	}
	
	@PostMapping("friend/accept/{request_id}")
	public ResponseEntity<String> accptRequest(@PathVariable("request_id") Long request_id, @AuthenticationPrincipal User user) {
		if (user == null)
			throw new UnauthorizedExeption("");
		FriendshipRequest friendshipRequest = friendshipRequestService.getFriendshipRequestById(request_id);
		if(friendshipRequest.getStatus() == Friendship_status.ACCEPTED || friendshipRequest.getStatus() == Friendship_status.REJECTED) throw new BadRequestExeption("");
		else if(friendshipRequest.getStatus() == Friendship_status.WAITING) {
			friendshipRequest.setStatus(Friendship_status.ACCEPTED);
			friendService.addFriend(user.getUser_id(), friendshipRequest.getSender().getUser_id());
			friendshipRequestService.save(friendshipRequest);
		}
		return new ResponseEntity<String>("you become " + friendshipRequest.getSender().getFull_name() + "'s friend", HttpStatus.OK);
	}

	@GetMapping("friend/requests")
	public ResponseEntity<ArrayList<FriendshipRequestDTO>> requestFriend(@AuthenticationPrincipal User user) {
		if (user == null)
			throw new UnauthorizedExeption("");
		ArrayList<FriendshipRequestDTO> friendshipRequestDTOs = friendService.getAllFriendshipRequest(user);
		return new ResponseEntity<>(friendshipRequestDTOs, HttpStatus.OK);
	}
	
	@DeleteMapping("friend/remove/{friend_id}")
	public ResponseEntity<String> removeFriend(@PathVariable("friend_id") Long friend_id, @AuthenticationPrincipal User user) {
		if (user == null)
			throw new UnauthorizedExeption("");
		User friend = userService.getUserById(friend_id);
		user = userService.getUserById(user.getUser_id());
		
		user.getFriends().remove(friend);
		friend.getFriends().remove(user);
		userService.save(user);
		userService.save(friend);
		return new ResponseEntity<String>("", HttpStatus.OK);
	}
}
