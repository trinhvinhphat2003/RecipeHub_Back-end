package com.example.RecipeHub.controllers.admin.apis;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.RecipeHub.dtos.RecipeDTO;
import com.example.RecipeHub.dtos.RecipesPaginationResponse;
import com.example.RecipeHub.entities.Recipe;
import com.example.RecipeHub.entities.User;
import com.example.RecipeHub.services.RecipeService;

@Controller
@RequestMapping("/api/v1/admin")
public class RecipeAdminController {
	
	private final RecipeService recipeService;
	
	public RecipeAdminController(RecipeService recipeService) {
		super();
		this.recipeService = recipeService;
	}

	@GetMapping("/recipes")
	public ResponseEntity<RecipesPaginationResponse> getAllRecipe(@AuthenticationPrincipal User user,
			@RequestParam(value = "page", defaultValue = "0", required = false) int page,
			@RequestParam(value = "size", defaultValue = "2", required = false) int size,
			@RequestParam(value = "query", defaultValue = "", required = false) String query,
			@RequestParam(value = "sort", defaultValue = "recipe_id", required = false) String sort,
			@RequestParam(value = "direction", defaultValue = "desc", required = false) String direction,
			@RequestParam(value = "verified", required = false) Boolean verified) {
		RecipesPaginationResponse response = recipeService.getRecipesWithPaginationAndFilter(query, page, size, sort, direction, verified);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@DeleteMapping("/recipe/{recipe_id}")
	public ResponseEntity<String> deleteOneRecipeByUser(@AuthenticationPrincipal User user,
			@PathVariable("recipe_id") Long recipeId) {
		recipeService.deleteOneRecipeById(recipeId);
		return new ResponseEntity<>("deleted recipe have id " + recipeId + " successfully", HttpStatus.OK);
	}
	
	@GetMapping("/recipe/total")
	public ResponseEntity<Integer> countRecipeCurrentInDB() {
		return ResponseEntity.ok(recipeService.countRecipeCurrentInDB());
	}
	
	@PostMapping("/recipe/{recipeId}/verify")
	public ResponseEntity<String> verifyRecipe(@PathVariable Long recipeId) {
		Recipe recipe = recipeService.getRecipeById(recipeId);
		recipe.setVerified(true);
		recipeService.save(recipe);
		return ResponseEntity.ok("this recipe have been verified");
	}
	
	@PostMapping("/recipe/{recipeId}/unverify")
	public ResponseEntity<String> unVerifyRecipe(@PathVariable Long recipeId) {
		Recipe recipe = recipeService.getRecipeById(recipeId);
		recipe.setVerified(false);
		recipeService.save(recipe);
		return ResponseEntity.ok("this recipe have been unverified");
	}
}
