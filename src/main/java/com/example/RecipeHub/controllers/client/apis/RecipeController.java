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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.RecipeHub.client.dtos.FIlterDTO;
import com.example.RecipeHub.client.dtos.IngredientDTO;
import com.example.RecipeHub.client.dtos.RecipeDTO;
import com.example.RecipeHub.client.dtos.TagDTO;
import com.example.RecipeHub.entities.Ingredient;
import com.example.RecipeHub.entities.Recipe;
import com.example.RecipeHub.entities.Tag;
import com.example.RecipeHub.entities.User;
import com.example.RecipeHub.enums.PrivacyStatus;
import com.example.RecipeHub.errorHandlers.UnauthorizedExeption;
import com.example.RecipeHub.mappers.RecipeMapper;
import com.example.RecipeHub.services.RecipeService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/")
public class RecipeController {

	private final RecipeService recipeService;

	public RecipeController(RecipeService recipeService) {
		super();
		this.recipeService = recipeService;
	}

	@PostMapping("user/recipe")
	public ResponseEntity<String> addNewRecipe(@RequestParam MultipartFile[] files,
			@RequestPart("data") RecipeDTO dto, @AuthenticationPrincipal User user, HttpServletRequest httpServletRequest) {
		dto.setRecipe_id(null);
		dto.setUserId(user.getUserId());
		recipeService.addNewRecipe(dto, files, user.getUserId(), httpServletRequest);
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

		ArrayList<RecipeDTO> recipeDtos = recipeService.getRecipesWithFilter(new FIlterDTO(null, null, null, null, null, null, PrivacyStatus.PUBLIC.name()), page, size, null);
		return new ResponseEntity<>(recipeDtos, HttpStatus.OK);
	}

	@PostMapping("global/recipes/filter")
	public ResponseEntity<ArrayList<RecipeDTO>> getAllRecipesWithFilter(@RequestBody FIlterDTO fIlterDTO,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "20") int size) {
		fIlterDTO.setPrivacyStatus(PrivacyStatus.PUBLIC.name());
		ArrayList<RecipeDTO> recipeDtos = recipeService.getRecipesWithFilter(fIlterDTO, page, size, null);
		return new ResponseEntity<>(recipeDtos, HttpStatus.OK);
	}
	
//	@PostMapping("global/recipes/filter/test")
//	public ResponseEntity<ArrayList<RecipeDTO>> getAllRecipesWithFilterTest(@RequestBody FIlterDTO fIlterDTO,
//			@RequestParam(value = "page", defaultValue = "0") int page,
//			@RequestParam(value = "size", defaultValue = "2") int size) {
//		ArrayList<RecipeDTO> recipeDtos = recipeService.getRecipesWithFilter(fIlterDTO, page, size, 1L);
//		return new ResponseEntity<>(recipeDtos, HttpStatus.OK);
//	}

	@PostMapping("user/recipes/filter")
	public ResponseEntity<ArrayList<RecipeDTO>> getAllRecipesByUserWithFilter(@AuthenticationPrincipal User user,
			@RequestBody FIlterDTO fIlterDTO, @RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "20") int size) {
		ArrayList<RecipeDTO> recipeDtos = recipeService.getRecipesWithFilter(fIlterDTO, page, size, user.getUserId());
		return new ResponseEntity<>(recipeDtos, HttpStatus.OK);
	}

	@PostMapping("global/recipes/filter/{user_id}")
	public ResponseEntity<ArrayList<RecipeDTO>> getAllRecipesWithFilter(@RequestBody FIlterDTO fIlterDTO,
			@PathVariable("user_id") Long user_id, @RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "20") int size) {
		fIlterDTO.setPrivacyStatus(PrivacyStatus.PUBLIC.name());
		ArrayList<RecipeDTO> recipeDtos = recipeService.getRecipesWithFilter(fIlterDTO, page, size, user_id);
		return new ResponseEntity<>(recipeDtos, HttpStatus.OK);
	}
}
