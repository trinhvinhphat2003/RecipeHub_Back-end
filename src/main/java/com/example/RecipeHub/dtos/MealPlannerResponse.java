package com.example.RecipeHub.dtos;

import java.util.Date;

import com.example.RecipeHub.entities.Recipe;
import com.example.RecipeHub.enums.MealType;

public class MealPlannerResponse {
	private Long mealPlannerId;
	private RecipeDTO recipe;
	private String mealType;
	private Long date;
	public Long getMealPlannerId() {
		return mealPlannerId;
	}
	public void setMealPlannerId(Long mealPlannerId) {
		this.mealPlannerId = mealPlannerId;
	}
	public RecipeDTO getRecipe() {
		return recipe;
	}
	public void setRecipe(RecipeDTO recipe) {
		this.recipe = recipe;
	}
	public String getMealType() {
		return mealType;
	}
	public void setMealType(String mealType) {
		this.mealType = mealType;
	}
	public Long getDate() {
		return date;
	}
	public void setDate(Long date) {
		this.date = date;
	}
	public MealPlannerResponse(Long mealPlannerId, RecipeDTO recipe, String mealType, Long date) {
		super();
		this.mealPlannerId = mealPlannerId;
		this.recipe = recipe;
		this.mealType = mealType;
		this.date = date;
	}
	public MealPlannerResponse() {
		super();
	}
	
	
}
