package com.example.RecipeHub.services;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.example.RecipeHub.client.dtos.UserDTO;
import com.example.RecipeHub.entities.User;
import com.example.RecipeHub.errorHandlers.NotFoundExeption;
import com.example.RecipeHub.repositories.UserRepository;

@Service
public class UserService {
	
	private final UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		super();
		this.userRepository = userRepository;
	}

	public User getUserById(Long user_id) {
		return userRepository.findById(user_id).orElseThrow(() -> new NotFoundExeption(""));
	}

	public void save(User user) {
		userRepository.save(user);		
	}

	public void blockUserByUserId(Long userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundExeption("user have id " + userId + " not found"));
		user.setBlocked(true);
		userRepository.save(user);
	}
	
	public void unBlockUserByUserId(Long userId) {
		User user = userRepository.findById(userId).orElseThrow(() -> new NotFoundExeption("user have id " + userId + " not found"));
		user.setBlocked(false);
		userRepository.save(user);
	}

	public int filterUser(String sort, String direction, String query) {
		// TODO Auto-generated method stub
		return 0;
	}

	public ArrayList<UserDTO> filterUserAndPagination(int page, int size, String sort, String direction, String query) {
		// TODO Auto-generated method stub
		return null;
	}
}
