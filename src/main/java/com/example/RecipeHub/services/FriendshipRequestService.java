package com.example.RecipeHub.services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.RecipeHub.entities.FriendshipRequest;
import com.example.RecipeHub.errorHandlers.NotFoundExeption;
import com.example.RecipeHub.repositories.FriendshipRepository;
import com.example.RecipeHub.repositories.FriendshipRequestRepository;

@Service
public class FriendshipRequestService {
	
	private final FriendshipRepository friendshipRepository;
	private final FriendshipRequestRepository friendshipRequestRepository;
	
	public FriendshipRequestService(FriendshipRepository friendshipRepository, FriendshipRequestRepository friendshipRequestRepository) {
		super();
		this.friendshipRepository = friendshipRepository;
		this.friendshipRequestRepository = friendshipRequestRepository;
	}

	public FriendshipRequest getFriendshipRequestById(Long friendshipRequestId) {
		return friendshipRequestRepository.findById(friendshipRequestId).orElseThrow(() -> new NotFoundExeption("this requst is not existed"));
	}

	public void save(FriendshipRequest friendshipRequest) {
		friendshipRequestRepository.save(friendshipRequest);
	}

	public void deleteById(Long friendshipRequestId) {
		FriendshipRequest friendshipRequest = friendshipRequestRepository.findById(friendshipRequestId).orElseThrow(() -> new NotFoundExeption("this requst is not existed"));
		friendshipRequestRepository.delete(friendshipRequest);
	}
}
