package com.example.RecipeHub.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.RecipeHub.entities.Ingredient;
import com.example.RecipeHub.entities.Recipe;
import com.example.RecipeHub.repositories.IngredientRepository;

@Service
public class IngredientService {
	
	private final IngredientRepository ingredientRepository;
	
	public IngredientService(IngredientRepository ingredientRepository) {
		super();
		this.ingredientRepository = ingredientRepository;
	}

	public void deleteAllByRecipe(Recipe recipe) {
		List<Ingredient> ingredients = recipe.getIngredients();
		ingredientRepository.deleteAll(ingredients);
	}
	
	public void deleteById(Long Id) {
		ingredientRepository.deleteById(Id);
	}
}
