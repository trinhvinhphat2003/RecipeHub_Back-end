package com.example.RecipeHub.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.RecipeHub.dtos.UserDTO;
import com.example.RecipeHub.entities.User;
import com.example.RecipeHub.errorHandlers.UserNotFoundExeption;
import com.example.RecipeHub.mappers.UserMapper;
import com.example.RecipeHub.repositories.UserRepository;

@Service
public class FriendService {
	

	private final UserRepository userRepository;
	
	public FriendService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	public void addFriend(Long user_id, Long friend_id) {
		Optional<User> user = userRepository.findById(user_id);
		Optional<User> friend = userRepository.findById(friend_id);
		if(user.isPresent() && friend.isPresent()) {
			user.get().getFriends().add(friend.get());
			friend.get().getFriends().add(user.get());
			userRepository.save(friend.get());
			userRepository.save(user.get());
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
}
