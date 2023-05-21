package com.example.RecipeHub.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.RecipeHub.entities.FriendshipRequest;
import com.example.RecipeHub.errorHandlers.NotFoundExeption;
import com.example.RecipeHub.repositories.FriendshipRepository;

@Service
public class FriendshipRequestService {
	
	private final FriendshipRepository friendshipRepository;
	
	public FriendshipRequestService(FriendshipRepository friendshipRepository) {
		super();
		this.friendshipRepository = friendshipRepository;
	}

	public FriendshipRequest getFriendshipRequestById(Long friendship_request_id) {
		return friendshipRepository.findById(friendship_request_id).orElseThrow(() -> new NotFoundExeption(""));
	}

	public void save(FriendshipRequest friendshipRequest) {
		friendshipRepository.save(friendshipRequest);
	}
}
