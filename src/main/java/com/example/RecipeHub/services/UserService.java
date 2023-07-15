package com.example.RecipeHub.services;

import java.util.ArrayList;

import com.example.RecipeHub.dtos.EditProfileRequest;
import com.example.RecipeHub.dtos.UserDTO;
import com.example.RecipeHub.entities.User;

public interface UserService {

	public User getUserById(Long user_id);

	public void save(User user);

	public void blockUserByUserId(Long userId);

	public void unBlockUserByUserId(Long userId);

	public int countOfFilterUser(String sort, String direction, String query, Integer isBlocked);

	public ArrayList<UserDTO> filterUserAndPagination(int page, int size, String sort, String direction, String query,
			Integer isBlocked);

	public void editProfile(EditProfileRequest request, Long userId);

	public Integer countUserCurrentInDB();

	public User findById(Long userId);
}
