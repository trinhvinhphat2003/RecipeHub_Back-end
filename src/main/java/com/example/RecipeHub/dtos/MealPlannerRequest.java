package com.example.RecipeHub.dtos;

import com.example.RecipeHub.enums.MealType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public class MealPlannerRequest {
	@NotNull(message = "Date is required")
	private Long date;
	@NotNull(message = "Recipe's id is required")
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
