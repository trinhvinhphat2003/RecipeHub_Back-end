package com.example.RecipeHub.controllers.client.apis;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.RecipeHub.dtos.FIlterDTO;
import com.example.RecipeHub.dtos.IngredientDTO;
import com.example.RecipeHub.dtos.RecipeDTO;
import com.example.RecipeHub.dtos.TagDTO;
import com.example.RecipeHub.entities.Ingredient;
import com.example.RecipeHub.entities.Recipe;
import com.example.RecipeHub.entities.Tag;
import com.example.RecipeHub.entities.User;
import com.example.RecipeHub.enums.PrivacyStatus;
import com.example.RecipeHub.errorHandlers.UnauthorizedExeption;
import com.example.RecipeHub.mappers.RecipeMapper;
import com.example.RecipeHub.services.RecipeService;

@RestController
@RequestMapping("/api/v1/")
public class RecipeController {

	private final RecipeService recipeService;

	public RecipeController(RecipeService recipeService) {
		super();
		this.recipeService = recipeService;
	}

	@PostMapping("user/recipe")
	public ResponseEntity<String> addNewRecipe(@RequestParam("images") MultipartFile[] imageFiles, @RequestBody RecipeDTO dto, @AuthenticationPrincipal User user) {
		recipeService.addNewRecipe(dto);
		
		
		return ResponseEntity.ok("ok");
	}
	
	@GetMapping("user/recipes")
	public ResponseEntity<ArrayList<RecipeDTO>> getAllRecipesForUser(@AuthenticationPrincipal User user) {
		if (user == null)
			throw new UnauthorizedExeption("");
		ArrayList<RecipeDTO> recipeDtos = recipeService.getAllRecipesByUser(user);
		return new ResponseEntity<>(recipeDtos, HttpStatus.OK);
	}
	
	@GetMapping("global/recipes")
	public ResponseEntity<ArrayList<RecipeDTO>> getAllRecipes(
			@RequestParam(value = "page", defaultValue = "0") int page,
	        @RequestParam(value = "size", defaultValue = "2") int size) {
		ArrayList<RecipeDTO> recipeDtos = recipeService.getAllRecipesByPrivacyStatus(PrivacyStatus.PUBLIC);
		return new ResponseEntity<>(recipeDtos, HttpStatus.OK);
	}
	
	@PostMapping("global/recipes/filter")
	public ResponseEntity<ArrayList<RecipeDTO>> getAllRecipesWithFilter(@RequestBody FIlterDTO fIlterDTO) {
		ArrayList<RecipeDTO> recipeDtos = recipeService.getAllPublicRecipesWithFilter(fIlterDTO);
		return new ResponseEntity<>(recipeDtos, HttpStatus.OK);
	}
	
	@PostMapping("user/recipes/filter")
	public ResponseEntity<ArrayList<RecipeDTO>> getAllRecipesByUserWithFilter(@AuthenticationPrincipal User user ,@RequestBody FIlterDTO fIlterDTO) {
		ArrayList<RecipeDTO> recipeDtos = recipeService.getAllUserRecipesWithFilter(fIlterDTO, user.getUserId());
		return new ResponseEntity<>(recipeDtos, HttpStatus.OK);
	}
	
	@PostMapping("global/recipes/filter/{user_id}")
	public ResponseEntity<ArrayList<RecipeDTO>> getAllRecipesWithFilter(@RequestBody FIlterDTO fIlterDTO, @PathVariable("user_id") Long user_id) {
		fIlterDTO.setPrivacyStatus("PUBLIC");
		ArrayList<RecipeDTO> recipeDtos = recipeService.getAllUserRecipesWithFilter(fIlterDTO, user_id);
		return new ResponseEntity<>(recipeDtos, HttpStatus.OK);
	}
}
