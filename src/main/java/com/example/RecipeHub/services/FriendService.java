package com.example.RecipeHub.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.RecipeHub.dtos.FriendshipRequestDTO;
import com.example.RecipeHub.dtos.UserDTO;
import com.example.RecipeHub.entities.FriendshipRequest;
import com.example.RecipeHub.entities.User;
import com.example.RecipeHub.enums.Friendship_status;
import com.example.RecipeHub.errorHandlers.NotFoundExeption;
import com.example.RecipeHub.errorHandlers.UserNotFoundExeption;
import com.example.RecipeHub.mappers.FriendshipRequestMapper;
import com.example.RecipeHub.mappers.UserMapper;
import com.example.RecipeHub.repositories.FriendshipRequestRepository;
import com.example.RecipeHub.repositories.UserRepository;

@Service
public class FriendService {
	

	private final UserRepository userRepository;
	private final FriendshipRequestRepository friendshipRequestRepository;
	
	public FriendService(UserRepository userRepository, FriendshipRequestRepository friendshipRequestRepository) {
		super();
		this.userRepository = userRepository;
		this.friendshipRequestRepository = friendshipRequestRepository;
	}

	public void addFriend(Long user_id, Long friend_id) {
		//get persist of user and friend
		Optional<User> userOp = userRepository.findById(user_id);
		Optional<User> friendOp = userRepository.findById(friend_id);
		
		//user to friend and friend to user
		if(userOp.isPresent() && friendOp.isPresent()) {
			User user = userOp.get();
			User friend = friendOp.get();
			user.getFriends().add(friend);
			friend.getFriends().add(user);
			userRepository.save(friend);
			userRepository.save(user);
		} else {
			throw new UserNotFoundExeption("");
		}
	}

	public ArrayList<UserDTO> getAllFriend(Long user_id) {
		Optional<User> user = userRepository.findById(user_id);
		if(user.isPresent()) {
			List<User> friends = user.get().getFriends();
			ArrayList<UserDTO> friendsDTO = new ArrayList<>();
			for(User friend : friends) {
				friendsDTO.add(UserMapper.INSTANCE.userToUserDTO(friend));
			}
			return friendsDTO;
		} else {
			throw new UserNotFoundExeption("");
		}
	}

	public ArrayList<FriendshipRequestDTO> getAllFriendshipRequest(User user) {
		user = userRepository.findById(user.getUserId()).orElseThrow(() -> new NotFoundExeption(""));
		ArrayList<FriendshipRequestDTO> friendshipRequestDTOs = new ArrayList<>();
		List<FriendshipRequest> friendshipRequests = friendshipRequestRepository.findAllByReceiverAndStatus(user, Friendship_status.PENDING);
		for(FriendshipRequest friendshipRequest : friendshipRequests) friendshipRequestDTOs.add(FriendshipRequestMapper.INSTANCE.friendshipRequestToFriendshipRequestDTO(friendshipRequest));
		return friendshipRequestDTOs;
	}

	public ArrayList<FriendshipRequestDTO> getAllSendedFriendshipRequest(User user) {
		user = userRepository.findById(user.getUserId()).orElseThrow(() -> new NotFoundExeption(""));
		ArrayList<FriendshipRequestDTO> friendshipRequestDTOs = new ArrayList<>();
		List<FriendshipRequest> friendshipRequests = friendshipRequestRepository.findAllBySenderAndStatus(user, Friendship_status.PENDING);
		for(FriendshipRequest friendshipRequest : friendshipRequests) friendshipRequestDTOs.add(FriendshipRequestMapper.INSTANCE.friendshipRequestToFriendshipRequestDTO(friendshipRequest));
		return friendshipRequestDTOs;
	}
}
