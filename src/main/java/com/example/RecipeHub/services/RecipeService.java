package com.example.RecipeHub.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.RecipeHub.dtos.FIlterDTO;
import com.example.RecipeHub.dtos.RecipeDTO;
import com.example.RecipeHub.entities.Ingredient;
import com.example.RecipeHub.entities.Recipe;
import com.example.RecipeHub.entities.Tag;
import com.example.RecipeHub.entities.User;
import com.example.RecipeHub.enums.PrivacyStatus;
import com.example.RecipeHub.mappers.RecipeMapper;
import com.example.RecipeHub.repositories.RecipeRepository;

@Service
public class RecipeService {
	
	private final RecipeRepository recipeRepository;
	
	public RecipeService(RecipeRepository recipeRepository) {
		super();
		this.recipeRepository = recipeRepository;
	}

	public ArrayList<RecipeDTO> getAllRecipesByUser(User user) {
		List<Recipe> recipes = user.getRecipes();
		ArrayList<RecipeDTO> recipeDTOs = new ArrayList<>();
		for(Recipe recipe : recipes) recipeDTOs.add(RecipeMapper.INSTANCE.recipeToRecipeDto(recipe));
		return recipeDTOs;
	}

	public void save(Recipe recipe) {
		recipeRepository.save(recipe);
	}

	public ArrayList<RecipeDTO> getAllRecipesByOrivacyStatus(PrivacyStatus privacyStatus) {
		List<Recipe> recipes = recipeRepository.findAllByPrivacyStatus(privacyStatus);
		ArrayList<RecipeDTO> recipeDTOs = new ArrayList<>();
		for(Recipe recipe : recipes) recipeDTOs.add(RecipeMapper.INSTANCE.recipeToRecipeDto(recipe));
		return recipeDTOs;
	}

	public ArrayList<RecipeDTO> getAllRecipesWithFilter(FIlterDTO fIlterDTO) {
		List<Recipe> recipes = new ArrayList<>();
		if(fIlterDTO.getTags().size() != 0 && fIlterDTO.getIngredients().size() != 0)
			recipes = recipeRepository.findByTagsAndIngredients(fIlterDTO.getTags(), fIlterDTO.getTags().size(), fIlterDTO.getIngredients(), fIlterDTO.getIngredients().size(), fIlterDTO.getTitle(), fIlterDTO.isFavorite(), PrivacyStatus.PUBLIC);
		else if(fIlterDTO.getTags().size() != 0)
			recipes = recipeRepository.findByTags(fIlterDTO.getTags(), fIlterDTO.getTags().size(), fIlterDTO.getTitle(), fIlterDTO.isFavorite(), PrivacyStatus.PUBLIC);
		else if(fIlterDTO.getIngredients().size() != 0)
			recipes = recipeRepository.findByIngredients(fIlterDTO.getIngredients(), fIlterDTO.getIngredients().size(), fIlterDTO.getTitle(), fIlterDTO.isFavorite(), PrivacyStatus.PUBLIC);
//				, PageRequest.of(1, 10));
		ArrayList<RecipeDTO> recipeDTOs = new ArrayList<>();
		for(Recipe recipe : recipes) recipeDTOs.add(RecipeMapper.INSTANCE.recipeToRecipeDto(recipe));
		return recipeDTOs;
	}
}
