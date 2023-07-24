package com.example.RecipeHub.services;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.example.RecipeHub.dtos.FriendshipRequestDTO;
import com.example.RecipeHub.dtos.UserDTO;
import com.example.RecipeHub.entities.User;

public interface FriendService {
	public void addFriend(Long user_id, Long friend_id);

	public ArrayList<UserDTO> getAllFriend(Long user_id);

	public ArrayList<FriendshipRequestDTO> getAllFriendshipRequest(User user);

	public ArrayList<FriendshipRequestDTO> getAllSendedFriendshipRequest(User user);
}
