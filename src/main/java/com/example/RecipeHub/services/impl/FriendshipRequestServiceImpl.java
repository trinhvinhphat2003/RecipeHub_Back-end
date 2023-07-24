package com.example.RecipeHub.services.impl;

import org.springframework.stereotype.Service;

import com.example.RecipeHub.entities.FriendshipRequest;
import com.example.RecipeHub.errorHandlers.NotFoundExeption;
import com.example.RecipeHub.repositories.FriendshipRepository;
import com.example.RecipeHub.repositories.FriendshipRequestRepository;
import com.example.RecipeHub.services.FriendshipRequestService;

@Service
public class FriendshipRequestServiceImpl implements FriendshipRequestService {
	private final FriendshipRequestRepository friendshipRequestRepository;

	public FriendshipRequestServiceImpl(FriendshipRequestRepository friendshipRequestRepository) {
		super();
		this.friendshipRequestRepository = friendshipRequestRepository;
	}

	@Override
	public FriendshipRequest getFriendshipRequestById(Long friendshipRequestId) {
		return friendshipRequestRepository.findById(friendshipRequestId)
				.orElseThrow(() -> new NotFoundExeption("this requst is not existed"));
	}

	@Override
	public void save(FriendshipRequest friendshipRequest) {
		friendshipRequestRepository.save(friendshipRequest);
	}

	@Override
	public void deleteById(Long friendshipRequestId) {
		FriendshipRequest friendshipRequest = friendshipRequestRepository.findById(friendshipRequestId)
				.orElseThrow(() -> new NotFoundExeption("this requst is not existed"));
		friendshipRequestRepository.delete(friendshipRequest);
	}
}
