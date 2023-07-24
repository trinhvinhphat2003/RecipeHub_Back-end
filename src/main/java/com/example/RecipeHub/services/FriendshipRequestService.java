package com.example.RecipeHub.services;

import com.example.RecipeHub.entities.FriendshipRequest;

public interface FriendshipRequestService {

	public FriendshipRequest getFriendshipRequestById(Long friendshipRequestId);

	public void save(FriendshipRequest friendshipRequest);

	public void deleteById(Long friendshipRequestId);
}
