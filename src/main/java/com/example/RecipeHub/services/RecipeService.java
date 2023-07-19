package com.example.RecipeHub.services;

import java.util.ArrayList;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.example.RecipeHub.dtos.FIlterDTO;
import com.example.RecipeHub.dtos.RecipeDTO;
import com.example.RecipeHub.dtos.RecipesPaginationResponse;
import com.example.RecipeHub.entities.Recipe;
import com.example.RecipeHub.entities.User;
import com.example.RecipeHub.enums.PrivacyStatus;

import jakarta.servlet.http.HttpServletRequest;

public interface RecipeService {

	public Pageable generatePageable(Integer page, Integer size, String sortBy, String direction);

	public RecipeDTO getRecipeDTOById(Long recipeId);

	public Recipe getRecipeById(long recipeId);

	public ArrayList<RecipeDTO> getAllRecipesByUser(User user);

	public void save(Recipe recipe);

	public ArrayList<RecipeDTO> getRecipesByPrivacyStatus(PrivacyStatus privacyStatus, Integer page, Integer size,
			String sortBy, String direction);

	public RecipesPaginationResponse getRecipesWithPaginationAndFilter(String query, int page, int size, String sort,
			String direction, Boolean isVerified);

	public ArrayList<RecipeDTO> getRecipesWithFilter(FIlterDTO fIlterDTO, int page, int size, Long userId, Boolean isVerified);

	public Long getTotalItemOfRecipesWithFilter(FIlterDTO fIlterDTO, int page, int size, Long userId, Boolean isVerified);

	public void addNewRecipe(RecipeDTO dto, MultipartFile[] imageFiles, Long userId,
			HttpServletRequest httpServletRequest);

	public void deleteOneUserRecipeById(Long recipeId, Long userId);

	public void deleteOneRecipeById(Long recipeId);

	public void editRecipe(RecipeDTO dto, Long recipeId, Long userId, MultipartFile[] files,
			HttpServletRequest httpServletRequest);

	public Long copyRecipe(User user, Long recipeId, HttpServletRequest httpServletRequest);

	public void sendSharedRecipeEmailUsingHtmlTemplate(String fullname, String email, Long recipeid) throws Exception;

	public Integer countRecipeCurrentInDB();

}
