package com.example.RecipeHub.services;

import org.springframework.stereotype.Service;

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
}
