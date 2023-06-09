package com.example.RecipeHub.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.RecipeHub.client.dtos.MealPlannerRequest;
import com.example.RecipeHub.client.dtos.MealPlannerResponse;
import com.example.RecipeHub.entities.Meal_planner;
import com.example.RecipeHub.entities.Recipe;
import com.example.RecipeHub.enums.MealType;
import com.example.RecipeHub.errorHandlers.ForbiddenExeption;
import com.example.RecipeHub.errorHandlers.NotFoundExeption;
import com.example.RecipeHub.mappers.RecipeMapper;
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
		List<Meal_planner> meal_Planners = mealPlannerRepository.findByDateFromTo(DateTimeUtil.milisecondToDateString(fromLong), DateTimeUtil.milisecondToDateString(toLong));
		ArrayList<MealPlannerResponse> result = new ArrayList<>();
		for (Meal_planner meal_Planner : meal_Planners) {
			result.add(new MealPlannerResponse(meal_Planner.getMealPlannerId(), RecipeMapper.INSTANCE.recipeToRecipeDto(meal_Planner.getRecipe()), meal_Planner.getMealType().name(), DateTimeUtil.dateToMilisecond(meal_Planner.getDate())));
		}
		return result;
	}

	public ArrayList<MealPlannerResponse> getMealPlannerByDate(Long dateLong) {
		String dateDate = DateTimeUtil.milisecondToDateString(dateLong);
		List<Meal_planner> meal_Planners = mealPlannerRepository.findByDate(dateDate);
		ArrayList<MealPlannerResponse> result = new ArrayList<>();
		for (Meal_planner meal_Planner : meal_Planners) {
			result.add(new MealPlannerResponse(meal_Planner.getMealPlannerId(), RecipeMapper.INSTANCE.recipeToRecipeDto(meal_Planner.getRecipe()), meal_Planner.getMealType().name(), DateTimeUtil.dateToMilisecond(meal_Planner.getDate())));
		}
		return result;
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

	public Meal_planner getOneById(Long mealPlannerId) {
		return mealPlannerRepository.findById(mealPlannerId).orElseThrow(() -> new NotFoundExeption("this planner is not existed"));
	}

	public void deleteById(Long mealPlannerId) {
		mealPlannerRepository.deleteById(mealPlannerId);
	}
}
