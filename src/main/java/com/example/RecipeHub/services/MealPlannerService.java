package com.example.RecipeHub.services;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.example.RecipeHub.client.dtos.MealPlannerRequest;
import com.example.RecipeHub.client.dtos.MealPlannerResponse;
import com.example.RecipeHub.entities.Meal_planner;
import com.example.RecipeHub.entities.Recipe;
import com.example.RecipeHub.enums.MealType;
import com.example.RecipeHub.errorHandlers.ForbiddenExeption;
import com.example.RecipeHub.errorHandlers.NotFoundExeption;
import com.example.RecipeHub.repositories.MealPlannerRepository;
import com.example.RecipeHub.utils.DateTimeUtil;

@Service
public class MealPlannerService {
	
	private final MealPlannerRepository mealPlannerRepository;
	private final RecipeService recipeService;
	
	public MealPlannerService(MealPlannerRepository mealPlannerRepository, RecipeService recipeService) {
		super();
		this.mealPlannerRepository = mealPlannerRepository;
		this.recipeService = recipeService;
	}

	public ArrayList<MealPlannerResponse> getMealPlannerFromTo(Long fromLong, Long toLong) {
		Date fromDate = DateTimeUtil.milisecondToDate(fromLong);
		Date toDate = DateTimeUtil.milisecondToDate(toLong);
		ArrayList<MealPlannerResponse> result = new ArrayList<>();
		return null;
	}

	public MealPlannerResponse getMealPlannerByDate(Long dateLong) {
		Date dateDate = DateTimeUtil.milisecondToDate(dateLong);
		Meal_planner meal_Planner = mealPlannerRepository.findByDate(dateDate).orElseThrow(() -> new NotFoundExeption("no meal planner in this date"));
		return null;
	}

	public void createNewMealPlanner(MealPlannerRequest request, Long userId) {
		Date date = DateTimeUtil.milisecondToDate(request.getDate());
		Recipe recipe = recipeService.getRecipeById(request.getRecipeId());
		if(recipe.getUser().getUserId() != userId) {
			throw new ForbiddenExeption("this is not your recipe");
		}
		MealType mealType = request.getMealType();
		
		mealPlannerRepository.save(new Meal_planner(null, recipe, recipe.getUser(), mealType, date));
	}
}
