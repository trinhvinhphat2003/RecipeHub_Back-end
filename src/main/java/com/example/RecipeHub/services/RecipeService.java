package com.example.RecipeHub.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.RecipeHub.dtos.FIlterDTO;
import com.example.RecipeHub.dtos.ImageDTO;
import com.example.RecipeHub.dtos.IngredientDTO;
import com.example.RecipeHub.dtos.RecipeDTO;
import com.example.RecipeHub.dtos.TagDTO;
import com.example.RecipeHub.entities.Ingredient;
import com.example.RecipeHub.entities.Recipe;
import com.example.RecipeHub.entities.Tag;
import com.example.RecipeHub.entities.User;
import com.example.RecipeHub.enums.PrivacyStatus;
import com.example.RecipeHub.errorHandlers.NotFoundExeption;
import com.example.RecipeHub.mappers.RecipeMapper;
import com.example.RecipeHub.repositories.RecipeCustomRepository;
import com.example.RecipeHub.repositories.RecipeRepository;
import com.example.RecipeHub.utils.FileUtil;
import com.example.RecipeHub.utils.PaginationUtil;

import jakarta.servlet.http.HttpServletRequest;

@Service
public class RecipeService {

	private final RecipeRepository recipeRepository;
	private final TagService tagService;
	private final RecipeCustomRepository recipeCustomRepository;

	public RecipeService(RecipeRepository recipeRepository, TagService tagService, RecipeCustomRepository recipeCustomRepository) {
		super();
		this.recipeRepository = recipeRepository;
		this.tagService = tagService;
		this.recipeCustomRepository = recipeCustomRepository;;
	}

	public Pageable generatePageable(Integer page, Integer size, String sortBy, String direction) {
		if(sortBy == null || sortBy.isEmpty()) return PageRequest.of(page, size);
		else if(direction.equals("desc")) return PageRequest.of(page, size, Sort.by(Direction.DESC ,sortBy));
		else if(direction.equals("asc")) return PageRequest.of(page, size, Sort.by(Direction.ASC ,sortBy));
		else return PageRequest.of(page, size, Sort.by(sortBy));
	} 
	
	public RecipeDTO getRecipeDTOById(Long recipeId) {
		Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new NotFoundExeption("recipe not found"));
		return RecipeMapper.INSTANCE.recipeToRecipeDto(recipe);
	}
	
	public Recipe getRecipeById(long recipeId) {
		Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new NotFoundExeption("recipe not found"));
		return recipe;
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

	public ArrayList<RecipeDTO> getRecipesByPrivacyStatus(PrivacyStatus privacyStatus, Integer page, Integer size, String sortBy, String direction) {
		Page<Recipe> recipes = recipeRepository.findAllByPrivacyStatus(privacyStatus, generatePageable(page, size, sortBy, direction));
		ArrayList<RecipeDTO> recipeDTOs = new ArrayList<>();
		for (Recipe recipe : recipes)
			recipeDTOs.add(RecipeMapper.INSTANCE.recipeToRecipeDto(recipe));
		return recipeDTOs;
	}


	public ArrayList<RecipeDTO> getRecipesWithPaginationAndFilter(String query, int page, int size) {
		Page<Recipe> recipes = recipeRepository.findByTitle(query, PaginationUtil.generatePageable(page, size, "recipe_id", "desc"));
		ArrayList<RecipeDTO> recipeDTOs = new ArrayList<>();
		for (Recipe recipe : recipes)
			recipeDTOs.add(RecipeMapper.INSTANCE.recipeToRecipeDto(recipe));
		return recipeDTOs;
	}

	public ArrayList<RecipeDTO> getRecipesWithFilter(FIlterDTO fIlterDTO, int page, int size, Long userId) {
		switch (fIlterDTO.getSortBy()) {
		case "id": {
			fIlterDTO.setSortBy("recipe_id");
			break;
		}
		case "title": {
			fIlterDTO.setSortBy("title");
			break;
		}
		case "rating": {
			fIlterDTO.setSortBy("rating");
			break;
		}
		case "yield": {
			fIlterDTO.setSortBy("recipe_yield");
			break;
		}
		case "time": {
			fIlterDTO.setSortBy("cook_time");
			break;
		}
		default:
			fIlterDTO.setSortBy("recipe_id");
		}
		
		switch (fIlterDTO.getDirection()){
		case "desc": {
			fIlterDTO.setDirection("desc");
			break;
		}
		case "asc": {
			fIlterDTO.setDirection("asc");
			break;
		}
		default:
			fIlterDTO.setDirection("desc");
		}
		List<Recipe> recipes = recipeCustomRepository.filterByCondition(fIlterDTO.getTags(), fIlterDTO.getIngredients(), page, size, fIlterDTO.getSortBy(), fIlterDTO.getDirection(), fIlterDTO.getTitle(), fIlterDTO.getPrivacyStatus(), fIlterDTO.isFavorite(), userId);
		ArrayList<RecipeDTO> recipeDTOs = new ArrayList<>();
		for (Recipe recipe : recipes)
			recipeDTOs.add(RecipeMapper.INSTANCE.recipeToRecipeDto(recipe));
		return recipeDTOs;
	}

	@Value("${recipe.image.upload.directory}")
	private String recipeImagePath;
	
	public void addNewRecipe(RecipeDTO dto, MultipartFile[] imageFiles, Long userId, HttpServletRequest httpServletRequest) {
		Recipe recipe = RecipeMapper.INSTANCE.recipeDtoToRecipe(dto);
		for(TagDTO tagDTO : dto.getTags()) {
			Tag tagPersisted = tagService.getTagByNameAndUserId(tagDTO.getTagName(), userId);
			if(tagPersisted != null) {
				recipe.getTags().add(tagPersisted);
			} else {
				recipe.getTags().add(new Tag(null, tagDTO.getTagName(), null));
			}
		}
		for(IngredientDTO ingredientDTO : dto.getIngredients()) {
			recipe.getIngredients().add(new Ingredient(null, recipe, ingredientDTO.getIngredientName(), ingredientDTO.getAmount()));
		}
		
		recipe = FileUtil.saveRecipeImage(imageFiles, recipe, recipeImagePath, httpServletRequest);
		recipeRepository.save(recipe);
		
	}

}
