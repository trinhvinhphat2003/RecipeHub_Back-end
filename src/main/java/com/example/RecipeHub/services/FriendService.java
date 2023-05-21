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
		Optional<User> userOp = userRepository.findById(user_id);
		Optional<User> friendOp = userRepository.findById(friend_id);
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
}
