package com.example.RecipeHub.services;

import com.example.RecipeHub.entities.Recipe;

public interface IngredientService {

	public void deleteAllByRecipe(Recipe recipe);

	public void deleteById(Long Id);
}
