package com.example.RecipeHub.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
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

import com.example.RecipeHub.admin.dtos.RecipesPaginationResponse;
import com.example.RecipeHub.client.dtos.FIlterDTO;
import com.example.RecipeHub.client.dtos.ImageDTO;
import com.example.RecipeHub.client.dtos.IngredientDTO;
import com.example.RecipeHub.client.dtos.RecipeDTO;
import com.example.RecipeHub.client.dtos.TagDTO;
import com.example.RecipeHub.entities.Ingredient;
import com.example.RecipeHub.entities.Recipe;
import com.example.RecipeHub.entities.Tag;
import com.example.RecipeHub.entities.User;
import com.example.RecipeHub.enums.PrivacyStatus;
import com.example.RecipeHub.errorHandlers.ForbiddenExeption;
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
	private final IngredientService ingredientService;

	public RecipeService(RecipeRepository recipeRepository, TagService tagService, RecipeCustomRepository recipeCustomRepository, IngredientService ingredientService) {
		super();
		this.recipeRepository = recipeRepository;
		this.tagService = tagService;
		this.recipeCustomRepository = recipeCustomRepository;
		this.ingredientService = ingredientService;;
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


	public RecipesPaginationResponse getRecipesWithPaginationAndFilter(String query, int page, int size, String sort, String direction) {
		List<Recipe> recipes = recipeCustomRepository.filterByCondition(null, null, page, size, sort, direction, query, null, null, null);
		Integer totalItem = recipeCustomRepository.getCountOfFilterByCondition(null, null, page, size, sort, direction, query, null, null, null);
		ArrayList<RecipeDTO> recipeDTOs = new ArrayList<>();
		for (Recipe recipe : recipes)
			recipeDTOs.add(RecipeMapper.INSTANCE.recipeToRecipeDto(recipe));
		return new RecipesPaginationResponse(recipeDTOs, totalItem);
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
		
		if(imageFiles != null) recipe = FileUtil.saveRecipeImage(imageFiles, recipe, recipeImagePath, httpServletRequest);
		recipeRepository.save(recipe);
		
	}

	public void deleteOneUserRecipeById(Long recipeId, Long userId) {
		Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new NotFoundExeption("recipe not found"));
		if(recipe.getUser().getUserId() != userId) throw new ForbiddenExeption("this recipe is not your");
		recipeRepository.delete(recipe);
	}
	
	public void deleteOneRecipeById(Long recipeId) {
		Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new NotFoundExeption("recipe not found"));
		recipeRepository.delete(recipe);
	}

	public void editRecipe(RecipeDTO dto, Long recipeId, Long userId) {
		Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new NotFoundExeption("thí recipe is not existed"));
		if(recipe.getUser().getUserId() != userId) throw new ForbiddenExeption("this is not your own recipe");
		
		recipe.setTitle(dto.getTitle());
		recipe.setPre_time(dto.getPre_time());
		recipe.setCook_time(dto.getCook_time());
		recipe.setRecipe_yield(dto.getRecipe_yield());
		recipe.setRating(dto.getRating());
		recipe.setIs_favourite(dto.isIs_favourite());
		recipe.setDescription(dto.getDescription());
		recipe.setUnit(dto.getUnit());
		recipe.setSteps(dto.getSteps());
		recipe.setPrivacyStatus(PrivacyStatus.valueOf(dto.getPrivacyStatus()));
		
		//tags
		ArrayList<TagDTO> tagDTOs = dto.getTags();
		
		Iterator<Tag> iterator = recipe.getTags().iterator();
		ArrayList<String> defautTags = new ArrayList<>();
		defautTags.add("Breakfast");
		defautTags.add("Lunch");
		defautTags.add("Dinner");
		defautTags.add("Appetizer");
		defautTags.add("Dessert");
		defautTags.add("Drink");
		defautTags.add("Snack");
		//delete tags
		while (iterator.hasNext()) {
			Tag tag = iterator.next();
			boolean check = true;
			for(TagDTO tagDTO : tagDTOs) {
				if(tag.getTagName().equals(tagDTO.getTagName())) {
					check = false;
					break;
				}
			}
			if(check) {
				if(defautTags.contains(tag.getTagName())) {
					recipe.getTags().remove(tag);
					iterator = recipe.getTags().iterator();
				} else {
					recipe.getTags().remove(tag);
					iterator = recipe.getTags().iterator();
				}
			}
		}
		//add tags
		for(TagDTO tagDTO : tagDTOs) {
			boolean check = true;
			for(Tag tag : recipe.getTags()) {
				if(tagDTO.getTagName().equals(tag.getTagName())) {
					check = false;
					break;
				}
			}
			if(check) {
				if(defautTags.contains(tagDTO.getTagName())) {
					Tag newTag = tagService.getByTagName(tagDTO.getTagName());
					recipe.getTags().add(newTag);
				} else {
					Tag newTag = null;
					try {
					newTag = tagService.getByTagName(tagDTO.getTagName());
					} catch (Exception e) {
						newTag = tagService.save(new Tag(tagDTO.getTagName()));
					}
					recipe.getTags().add(newTag);
				}
			}
		}
		
		//ingredients
		ArrayList<IngredientDTO> ingredientDTOs = dto.getIngredients();
		Iterator<Ingredient> iterator2 = recipe.getIngredients().iterator();
		while(iterator2.hasNext()) {
			Ingredient ingredient = iterator2.next();
			recipe.getIngredients().remove(ingredient);
			ingredientService.deleteById(ingredient.getIngredientId());
			iterator2 = recipe.getIngredients().iterator();
		}
		recipe = recipeRepository.save(recipe);
		for(IngredientDTO ingredientDTO : ingredientDTOs) {
			recipe.getIngredients().add(new Ingredient(null, recipe, ingredientDTO.getIngredientName(), ingredientDTO.getAmount()));
		}
		
		//image
		ArrayList<ImageDTO> imageDTOs = dto.getImages();
		for(ImageDTO imageDTO : imageDTOs) {
			
		}
		
		recipeRepository.save(recipe);
	}

}
