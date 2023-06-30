package com.example.RecipeHub.client.dtos;

import com.example.RecipeHub.enums.MealType;

public class MealPlannerRequest {
	private Long date;
	private Long recipeId;
	private MealType mealType;
	public MealPlannerRequest(Long date, Long recipeId, MealType mealType) {
		super();
		this.date = date;
		this.recipeId = recipeId;
		this.mealType = mealType;
	}
	public MealPlannerRequest() {
		super();
	}
	public Long getDate() {
		return date;
	}
	public void setDate(Long date) {
		this.date = date;
	}
	public Long getRecipeId() {
		return recipeId;
	}
	public void setRecipeId(Long recipeId) {
		this.recipeId = recipeId;
	}
	public MealType getMealType() {
		return mealType;
	}
	public void setMealType(MealType mealType) {
		this.mealType = mealType;
	}
}
