package com.example.RecipeHub.controllers.admin.apis;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.RecipeHub.admin.dtos.RecipesPaginationResponse;
import com.example.RecipeHub.client.dtos.RecipeDTO;
import com.example.RecipeHub.entities.Recipe;
import com.example.RecipeHub.entities.User;
import com.example.RecipeHub.services.RecipeService;

@Controller
@RequestMapping("/api/v1")
public class RecipeAdminController {
	
	private final RecipeService recipeService;
	
	public RecipeAdminController(RecipeService recipeService) {
		super();
		this.recipeService = recipeService;
	}

	@GetMapping("/admin/recipes")
	public ResponseEntity<RecipesPaginationResponse> getAllRecipe(@AuthenticationPrincipal User user,
			@RequestParam(value = "page", defaultValue = "0", required = false) int page,
			@RequestParam(value = "size", defaultValue = "2", required = false) int size,
			@RequestParam(value = "query", defaultValue = "", required = false) String query,
			@RequestParam(value = "sort", defaultValue = "recipe_id", required = false) String sort,
			@RequestParam(value = "direction", defaultValue = "desc", required = false) String direction) {
		RecipesPaginationResponse response = recipeService.getRecipesWithPaginationAndFilter(query, page, size, sort, direction);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@DeleteMapping("/admin/recipe/{recipe_id}")
	public ResponseEntity<String> deleteOneRecipeByUser(@AuthenticationPrincipal User user,
			@PathVariable("recipe_id") Long recipeId) {
		recipeService.deleteOneRecipeById(recipeId);
		return new ResponseEntity<>("deleted recipe have id " + recipeId + " successfully", HttpStatus.OK);
	}
}
