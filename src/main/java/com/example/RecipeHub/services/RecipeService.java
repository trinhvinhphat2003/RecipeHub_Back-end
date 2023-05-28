package com.example.RecipeHub.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Collections;

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
import com.example.RecipeHub.errorHandlers.NotFoundExeption;
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

	public ArrayList<RecipeDTO> getAllRecipesByPrivacyStatus(PrivacyStatus privacyStatus) {
		List<Recipe> recipes = recipeRepository.findAllByPrivacyStatus(privacyStatus);
		ArrayList<RecipeDTO> recipeDTOs = new ArrayList<>();
		for(Recipe recipe : recipes) recipeDTOs.add(RecipeMapper.INSTANCE.recipeToRecipeDto(recipe));
		return recipeDTOs;
	}

	public ArrayList<RecipeDTO> getAllPublicRecipesWithFilter(FIlterDTO fIlterDTO) {
		List<Recipe> recipes = new ArrayList<>();
//		ArrayList<String> tags = fIlterDTO.getTags();
//		ArrayList<String> ingredients = fIlterDTO.getIngredients();
//		long tagCount = 0;
//		long ingredientCount = 0;
//		
//		if(tags.size() == 0) {
//			tags = null;
//		} else {
//			tagCount = tags.size();
//		}
//		if(ingredients.size() == 0) {
//			ingredients = null;
//		} else {
//			ingredientCount = ingredients.size();
//		}
//		
//		recipes = recipeRepository.findByTagsAndIngredients(tags, tagCount, ingredients, ingredientCount, fIlterDTO.getTitle(), fIlterDTO.isFavorite(), PrivacyStatus.PUBLIC);
//		
		if(fIlterDTO.getTags().size() == 0 && fIlterDTO.getIngredients().size() == 0) {
			recipes = recipeRepository.findAllByPrivacyStatus(PrivacyStatus.PUBLIC);
		}
		else if(fIlterDTO.getTags().size() != 0 && fIlterDTO.getIngredients().size() != 0)
			recipes = recipeRepository.findByTagsAndIngredients(fIlterDTO.getTags(), fIlterDTO.getTags().size(), fIlterDTO.getIngredients(), fIlterDTO.getIngredients().size(), fIlterDTO.getTitle(), fIlterDTO.isFavorite(), PrivacyStatus.PUBLIC);
		else if(fIlterDTO.getTags().size() != 0)
			recipes = recipeRepository.findByTags(fIlterDTO.getTags(), fIlterDTO.getTags().size(), fIlterDTO.getTitle(), fIlterDTO.isFavorite(), PrivacyStatus.PUBLIC);
		else if(fIlterDTO.getIngredients().size() != 0)
			recipes = recipeRepository.findByIngredients(fIlterDTO.getIngredients(), fIlterDTO.getIngredients().size(), fIlterDTO.getTitle(), fIlterDTO.isFavorite(), PrivacyStatus.PUBLIC);
		ArrayList<RecipeDTO> recipeDTOs = new ArrayList<>();
		for(Recipe recipe : recipes) recipeDTOs.add(RecipeMapper.INSTANCE.recipeToRecipeDto(recipe));
		return recipeDTOs;
	}

	public ArrayList<RecipeDTO> getAllUserRecipesWithFilter(FIlterDTO fIlterDTO, Long user_id) {
		List<Recipe> recipes = new ArrayList<>();
		PrivacyStatus privacyStatus;
		
		switch (fIlterDTO.getPrivacyStatus()){
		case "PUBLIC": {
			privacyStatus = PrivacyStatus.PUBLIC;
			break;
		}
		case "PRIVATE": {
			privacyStatus = PrivacyStatus.PRIVATE;
			break;
		}
		default:
			privacyStatus = null;
		}
		
//		recipes = recipeRepository.findByTagsAndIngredientsAndUser(tags, tagCount, ingredients, ingredientCount, fIlterDTO.getTitle(), fIlterDTO.isFavorite(), privacyStatus, user.getUserId());
		if(fIlterDTO.getTags().size() == 0 && fIlterDTO.getIngredients().size() == 0) {
			recipes = recipeRepository.findAllUserRecipesByPrivacyStatus(privacyStatus, user_id);
		}
		else if(fIlterDTO.getTags().size() != 0 && fIlterDTO.getIngredients().size() != 0) {
			recipes = recipeRepository.findByTagsAndIngredientsAndUser(fIlterDTO.getTags(), fIlterDTO.getTags().size(), fIlterDTO.getIngredients(), fIlterDTO.getIngredients().size(), fIlterDTO.getTitle(), fIlterDTO.isFavorite(), privacyStatus, user_id);
		}
		else if(fIlterDTO.getTags().size() != 0)
		recipes = recipeRepository.findByTagsAndUser(fIlterDTO.getTags(), fIlterDTO.getTags().size(), fIlterDTO.getTitle(), fIlterDTO.isFavorite(), privacyStatus, user_id);
		else if(fIlterDTO.getIngredients().size() != 0)
			recipes = recipeRepository.findByIngredientsAndUser(fIlterDTO.getIngredients(), fIlterDTO.getIngredients().size(), fIlterDTO.getTitle(), fIlterDTO.isFavorite(), privacyStatus, user_id);
		ArrayList<RecipeDTO> recipeDTOs = new ArrayList<>();
		for(Recipe recipe : recipes) recipeDTOs.add(RecipeMapper.INSTANCE.recipeToRecipeDto(recipe));
		return recipeDTOs;
	}
	
	
	
}
