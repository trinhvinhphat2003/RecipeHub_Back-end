package com.example.RecipeHub.services.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.RecipeHub.entities.Ingredient;
import com.example.RecipeHub.entities.Recipe;
import com.example.RecipeHub.repositories.IngredientRepository;
import com.example.RecipeHub.services.IngredientService;

@Service
public class IngredientServiceImpl implements IngredientService{
private final IngredientRepository ingredientRepository;
	
	public IngredientServiceImpl(IngredientRepository ingredientRepository) {
		super();
		this.ingredientRepository = ingredientRepository;
	}

	@Override
	public void deleteAllByRecipe(Recipe recipe) {
		List<Ingredient> ingredients = recipe.getIngredients();
		ingredientRepository.deleteAll(ingredients);
	}
	
	@Override
	public void deleteById(Long Id) {
		ingredientRepository.deleteById(Id);
	}
}
