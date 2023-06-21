package com.example.RecipeHub.controllers.admin.apis;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.RecipeHub.dtos.RecipeDTO;
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
	public ResponseEntity<ArrayList<RecipeDTO>> getAllRecipe(@AuthenticationPrincipal User user,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "2") int size,
			@RequestParam(value = "query", defaultValue = "") String query,
			@RequestParam(value = "sort", defaultValue = "id") String sort,
			@RequestParam(value = "direction", defaultValue = "desc") String direction) {
		ArrayList<RecipeDTO> recipeDtos = recipeService.getRecipesWithPaginationAndFilter(query, page, size, sort, direction);
		return new ResponseEntity<>(recipeDtos, HttpStatus.OK);
	}
}
