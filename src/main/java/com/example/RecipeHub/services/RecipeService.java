package com.example.RecipeHub.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Collections;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import com.example.RecipeHub.dtos.FIlterDTO;
import com.example.RecipeHub.dtos.ImageDTO;
import com.example.RecipeHub.dtos.IngredientDTO;
import com.example.RecipeHub.dtos.RecipeDTO;
import com.example.RecipeHub.dtos.TagDTO;
import com.example.RecipeHub.entities.Ingredient;
import com.example.RecipeHub.entities.Recipe;
import com.example.RecipeHub.entities.Recipe_HAVE_Ingredient;
import com.example.RecipeHub.entities.Tag;
import com.example.RecipeHub.entities.User;
import com.example.RecipeHub.enums.PrivacyStatus;
import com.example.RecipeHub.errorHandlers.NotFoundExeption;
import com.example.RecipeHub.mappers.RecipeMapper;
import com.example.RecipeHub.repositories.RecipeRepository;

@Service
public class RecipeService {

	private final RecipeRepository recipeRepository;
	private final TagService tagService;

	public RecipeService(RecipeRepository recipeRepository, TagService tagService) {
		super();
		this.recipeRepository = recipeRepository;
		this.tagService = tagService;
	}

	public Pageable generatePageable(Integer page, Integer size, String sortBy, String direction) {
		if(sortBy == null || sortBy.isEmpty()) return PageRequest.of(page, size);
		else if(direction.equals("desc")) return PageRequest.of(page, size, Sort.by(Direction.DESC ,sortBy));
		else if(direction.equals("asc")) return PageRequest.of(page, size, Sort.by(Direction.ASC ,sortBy));
		else return PageRequest.of(page, size, Sort.by(sortBy));
	} 
	
	public RecipeDTO getRecipeById(Long recipeId) {
		Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new NotFoundExeption("recipe not found"));
		return RecipeMapper.INSTANCE.recipeToRecipeDto(recipe);
	}
	
	public ArrayList<RecipeDTO> getAllRecipesByUser(User user) {
		List<Recipe> recipes = user.getRecipes();
		ArrayList<RecipeDTO> recipeDTOs = new ArrayList<>();
		for (Recipe recipe : recipes)
			recipeDTOs.add(RecipeMapper.INSTANCE.recipeToRecipeDto(recipe));
		return recipeDTOs;
	}

	public void save(Recipe recipe) {
		recipeRepository.save(recipe);
	}

	public ArrayList<RecipeDTO> getAllRecipesByPrivacyStatus(PrivacyStatus privacyStatus) {
		Page<Recipe> recipes = recipeRepository.findAllByPrivacyStatus(privacyStatus, generatePageable(0, 3, "recipe_id", "as"));
		ArrayList<RecipeDTO> recipeDTOs = new ArrayList<>();
		for (Recipe recipe : recipes)
			recipeDTOs.add(RecipeMapper.INSTANCE.recipeToRecipeDto(recipe));
		return recipeDTOs;
	}

	public ArrayList<RecipeDTO> getAllPublicRecipesWithFilter(FIlterDTO fIlterDTO) {
		List<Recipe> recipes = new ArrayList<>();
		/*
		 * the logic below is mainly to deal with tag and ingredient, the additional
		 * information as title, sort by, ... is attached already the logic of
		 * getAllUserRecipesWithFilter is also the same but it have user_id as
		 * additional
		 */
		// if client not filter with tag and ingredient
		if (fIlterDTO.getTags().size() == 0 && fIlterDTO.getIngredients().size() == 0) {
			recipes = recipeRepository.findAllGlobalRecipeByPrivacyStatus(fIlterDTO.getTitle(), fIlterDTO.isFavorite(),
					PrivacyStatus.PUBLIC);
		}
		// if client filter with both tag and ingredient
		else if (fIlterDTO.getTags().size() != 0 && fIlterDTO.getIngredients().size() != 0)
			recipes = recipeRepository.findByTagsAndIngredients(fIlterDTO.getTags(), fIlterDTO.getTags().size(),
					fIlterDTO.getIngredients(), fIlterDTO.getIngredients().size(), fIlterDTO.getTitle(),
					fIlterDTO.isFavorite(), PrivacyStatus.PUBLIC);
		// if client filter with only tag
		else if (fIlterDTO.getTags().size() != 0)
			recipes = recipeRepository.findByTags(fIlterDTO.getTags(), fIlterDTO.getTags().size(), fIlterDTO.getTitle(),
					fIlterDTO.isFavorite(), PrivacyStatus.PUBLIC);
		// if client filter with only ingredient
		else if (fIlterDTO.getIngredients().size() != 0)
			recipes = recipeRepository.findByIngredients(fIlterDTO.getIngredients(), fIlterDTO.getIngredients().size(),
					fIlterDTO.getTitle(), fIlterDTO.isFavorite(), PrivacyStatus.PUBLIC);
		ArrayList<RecipeDTO> recipeDTOs = new ArrayList<>();
		for (Recipe recipe : recipes)
			recipeDTOs.add(RecipeMapper.INSTANCE.recipeToRecipeDto(recipe));
		return recipeDTOs;
	}

	public ArrayList<RecipeDTO> getAllUserRecipesWithFilter(FIlterDTO fIlterDTO, Long user_id) {
		List<Recipe> recipes = new ArrayList<>();
		PrivacyStatus privacyStatus;

		switch (fIlterDTO.getPrivacyStatus()) {
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
		if (fIlterDTO.getTags().size() == 0 && fIlterDTO.getIngredients().size() == 0) {
			recipes = recipeRepository.findAllUserRecipesByPrivacyStatus(fIlterDTO.getTitle(), fIlterDTO.isFavorite(),
					privacyStatus, user_id);
		} else if (fIlterDTO.getTags().size() != 0 && fIlterDTO.getIngredients().size() != 0) {
			recipes = recipeRepository.findByTagsAndIngredientsAndUser(fIlterDTO.getTags(), fIlterDTO.getTags().size(),
					fIlterDTO.getIngredients(), fIlterDTO.getIngredients().size(), fIlterDTO.getTitle(),
					fIlterDTO.isFavorite(), privacyStatus, user_id);
		} else if (fIlterDTO.getTags().size() != 0)
			recipes = recipeRepository.findByTagsAndUser(fIlterDTO.getTags(), fIlterDTO.getTags().size(),
					fIlterDTO.getTitle(), fIlterDTO.isFavorite(), privacyStatus, user_id);
		else if (fIlterDTO.getIngredients().size() != 0)
			recipes = recipeRepository.findByIngredientsAndUser(fIlterDTO.getIngredients(),
					fIlterDTO.getIngredients().size(), fIlterDTO.getTitle(), fIlterDTO.isFavorite(), privacyStatus,
					user_id);
		ArrayList<RecipeDTO> recipeDTOs = new ArrayList<>();
		for (Recipe recipe : recipes)
			recipeDTOs.add(RecipeMapper.INSTANCE.recipeToRecipeDto(recipe));
		return recipeDTOs;
	}

	public void addNewRecipe(RecipeDTO dto) {
		Recipe recipe = RecipeMapper.INSTANCE.recipeDtoToRecipe(dto);
		recipe = recipeRepository.save(recipe);
		ArrayList<TagDTO> tagDtos = dto.getTags();
		ArrayList<ImageDTO> imageDTOs = dto.getImages();
		ArrayList<IngredientDTO> ingredientDTOs = dto.getIngredients();

		for (TagDTO tagDTO : tagDtos) {
			Tag tag = tagService.getTagByName(tagDTO.getTagName());
			recipe.getTags().add(tag);
		}

		for (ImageDTO imageDTO : imageDTOs) {
			
		}

		for (IngredientDTO ingredientDTO : ingredientDTOs) {

		}

		recipeRepository.save(recipe);
	}

}
