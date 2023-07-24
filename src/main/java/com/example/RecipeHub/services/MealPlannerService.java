package com.example.RecipeHub.services;

import java.util.ArrayList;

import com.example.RecipeHub.dtos.MealPlannerRequest;
import com.example.RecipeHub.dtos.MealPlannerResponse;
import com.example.RecipeHub.entities.Meal_planner;

public interface MealPlannerService {

	public ArrayList<MealPlannerResponse> getMealPlannerFromTo(Long fromLong, Long toLong, Long userId);

	public ArrayList<MealPlannerResponse> getMealPlannerByDate(Long dateLong, Long userId);

	public void createNewMealPlanner(MealPlannerRequest request, Long userId);

	public Meal_planner getOneById(Long mealPlannerId);

	public void deleteById(Long mealPlannerId);
}
