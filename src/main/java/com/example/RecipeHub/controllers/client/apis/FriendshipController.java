package com.example.RecipeHub.controllers.client.apis;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.RecipeHub.client.dtos.FriendshipRequestDTO;
import com.example.RecipeHub.client.dtos.UserDTO;
import com.example.RecipeHub.entities.FriendshipRequest;
import com.example.RecipeHub.entities.User;
import com.example.RecipeHub.enums.Friendship_status;
import com.example.RecipeHub.errorHandlers.BadRequestExeption;
import com.example.RecipeHub.errorHandlers.UnauthorizedExeption;
import com.example.RecipeHub.repositories.FriendshipRepository;
import com.example.RecipeHub.services.FriendService;
import com.example.RecipeHub.services.FriendshipRequestService;
import com.example.RecipeHub.services.UserService;

@RestController
@RequestMapping("/api/v1/")
public class FriendshipController {

	private final UserService userService;
	private final FriendService friendService;
	private final FriendshipRequestService friendshipRequestService;

	public FriendshipController(UserService userService,
			FriendService friendService, FriendshipRequestService friendshipRequestService) {
		super();
		this.userService = userService;
		this.friendService = friendService;
		this.friendshipRequestService = friendshipRequestService;
	}

	@GetMapping("user/friends")
	public ResponseEntity<ArrayList<UserDTO>> getFriends(@AuthenticationPrincipal User user) {
		if (user == null)
			throw new UnauthorizedExeption("");
		ArrayList<UserDTO> friends = friendService.getAllFriend(user.getUserId());
		return new ResponseEntity<>(friends, HttpStatus.OK);
	}

	@PostMapping("user/request-friend/{receiver_id}")
	public ResponseEntity<String> getAllRequest(@PathVariable("receiver_id") Long receiver_id,
			@AuthenticationPrincipal User user) {
		if (user == null)
			throw new UnauthorizedExeption("");
		User receiver = userService.getUserById(receiver_id);
		if(receiver.getUserId() == user.getUserId()) throw new BadRequestExeption("you can not request to yourself");
		FriendshipRequest friendshipRequest = new FriendshipRequest(user, receiver, Friendship_status.PENDING);
		friendshipRequestService.save(friendshipRequest);
		return new ResponseEntity<String>("you have send friend's request to " + receiver.getFullName(),
				HttpStatus.OK);
	}

	@PostMapping("user/accept-friend/{request_id}")
	public ResponseEntity<String> acceptRequest(@PathVariable("request_id") Long request_id,
			@AuthenticationPrincipal User user) {
		if (user == null)
			throw new UnauthorizedExeption("");
		FriendshipRequest friendshipRequest = friendshipRequestService.getFriendshipRequestById(request_id);
		if (friendshipRequest.getStatus() == Friendship_status.ACCEPTED
				|| friendshipRequest.getStatus() == Friendship_status.REJECTED)
			throw new BadRequestExeption("");
		else if (friendshipRequest.getStatus() == Friendship_status.PENDING) {
			friendshipRequest.setStatus(Friendship_status.ACCEPTED);
			friendshipRequestService.deleteById(friendshipRequest.getFriendshipRequestId());
			friendService.addFriend(user.getUserId(), friendshipRequest.getSender().getUserId());
			friendshipRequestService.save(friendshipRequest);
		}
		return new ResponseEntity<String>("you become " + friendshipRequest.getSender().getFullName() + "'s friend",
				HttpStatus.OK);
	}
	
	@PostMapping("user/reject-friend/{request_id}")
	public ResponseEntity<String> rejectRequest(@PathVariable("request_id") Long request_id,
			@AuthenticationPrincipal User user) {
		FriendshipRequest friendshipRequest = friendshipRequestService.getFriendshipRequestById(request_id);
		String nameSender = friendshipRequest.getSender().getFullName();
		friendshipRequestService.deleteById(request_id);
		return ResponseEntity.ok("you have rejected friend request from " + nameSender);
	}

	@GetMapping("user/friend/requests")
	public ResponseEntity<ArrayList<FriendshipRequestDTO>> requestFriend(@AuthenticationPrincipal User user) {
		if (user == null)
			throw new UnauthorizedExeption("");
		ArrayList<FriendshipRequestDTO> friendshipRequestDTOs = friendService.getAllFriendshipRequest(user);
		return new ResponseEntity<>(friendshipRequestDTOs, HttpStatus.OK);
	}
	
	@GetMapping("user/friend/sended-requests")
	public ResponseEntity<ArrayList<FriendshipRequestDTO>> getSendedRequest(@AuthenticationPrincipal User user) {
		if (user == null)
			throw new UnauthorizedExeption("");
		ArrayList<FriendshipRequestDTO> friendshipRequestDTOs = friendService.getAllSendedFriendshipRequest(user);
		return new ResponseEntity<>(friendshipRequestDTOs, HttpStatus.OK);
	}

	@DeleteMapping("user/remove-friend/{friend_id}")
	public ResponseEntity<String> removeFriend(@PathVariable("friend_id") Long friend_id,
			@AuthenticationPrincipal User user) {
		if (user == null)
			throw new UnauthorizedExeption("");
		User friend = userService.getUserById(friend_id);
		if(friend.getUserId() == user.getUserId()) throw new BadRequestExeption("you can not remove yourself");
		user = userService.getUserById(user.getUserId());

		user.getFriends().remove(friend);
		friend.getFriends().remove(user);
		userService.save(user);
		userService.save(friend);
		return new ResponseEntity<String>("you have been unfriend with " + friend.getFullName(), HttpStatus.OK);
	}
}
