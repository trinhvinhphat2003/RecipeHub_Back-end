package com.example.RecipeHub.services;

import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.example.RecipeHub.client.dtos.UserDTO;
import com.example.RecipeHub.entities.User;
import com.example.RecipeHub.errorHandlers.NotFoundExeption;
import com.example.RecipeHub.mappers.UserMapper;
import com.example.RecipeHub.repositories.UserRepository;
import com.example.RecipeHub.utils.PaginationUtil;

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
		return userRepository.filterUser(query).size();
	}

	public ArrayList<UserDTO> filterUserAndPagination(int page, int size, String sort, String direction, String query) {
		Page<User> users = userRepository.filterUserAndPagination(query, sort, direction, PaginationUtil.generatePageable(page, size, sort, direction));
		ArrayList<UserDTO> result = new ArrayList<>();
		for(User user : users) {
			result.add(UserMapper.INSTANCE.userToUserDTO(user));
		}
		return result;
	}
}
