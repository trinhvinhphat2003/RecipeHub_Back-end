package com.example.RecipeHub.controllers.client.apis;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.RecipeHub.dtos.RecipeDTO;
import com.example.RecipeHub.entities.User;
import com.example.RecipeHub.errorHandlers.UnauthorizedExeption;
import com.example.RecipeHub.services.RecipeService;

@RestController
@RequestMapping("/api/v1/")
public class RecipeController {

	private final RecipeService recipeService;

	public RecipeController(RecipeService recipeService) {
		super();
		this.recipeService = recipeService;
	}

	@GetMapping("user/recipes")
	public ResponseEntity<ArrayList<RecipeDTO>> getAllRecipesForUser(@AuthenticationPrincipal User user) {
		if (user == null)
			throw new UnauthorizedExeption("");
		ArrayList<RecipeDTO> recipeDtos = recipeService.getAllRecipesByUser(user);
		return new ResponseEntity<>(recipeDtos, HttpStatus.OK);
	}
}
