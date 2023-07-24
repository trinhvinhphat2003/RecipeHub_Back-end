package com.example.RecipeHub.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.RecipeHub.dtos.MealPlannerRequest;
import com.example.RecipeHub.dtos.MealPlannerResponse;
import com.example.RecipeHub.entities.Meal_planner;
import com.example.RecipeHub.entities.Recipe;
import com.example.RecipeHub.enums.MealType;
import com.example.RecipeHub.errorHandlers.ForbiddenExeption;
import com.example.RecipeHub.errorHandlers.NotFoundExeption;
import com.example.RecipeHub.mappers.RecipeMapper;
import com.example.RecipeHub.repositories.MealPlannerRepository;
import com.example.RecipeHub.services.MealPlannerService;
import com.example.RecipeHub.services.RecipeService;
import com.example.RecipeHub.utils.DateTimeUtil;

@Service
public class MealPlannerServiceImpl implements MealPlannerService {
	private final MealPlannerRepository mealPlannerRepository;
	private final RecipeService recipeService;
	
	public MealPlannerServiceImpl(MealPlannerRepository mealPlannerRepository, RecipeService recipeService) {
		super();
		this.mealPlannerRepository = mealPlannerRepository;
		this.recipeService = recipeService;
	}

	@Override
	public ArrayList<MealPlannerResponse> getMealPlannerFromTo(Long fromLong, Long toLong, Long userId) {
		List<Meal_planner> meal_Planners = mealPlannerRepository.findByDateFromTo(DateTimeUtil.milisecondToDateString(fromLong), DateTimeUtil.milisecondToDateString(toLong), userId);
		ArrayList<MealPlannerResponse> result = new ArrayList<>();
		for (Meal_planner meal_Planner : meal_Planners) {
			result.add(new MealPlannerResponse(meal_Planner.getMealPlannerId(), RecipeMapper.INSTANCE.recipeToRecipeDto(meal_Planner.getRecipe()), meal_Planner.getMealType().name(), DateTimeUtil.dateToMilisecond(meal_Planner.getDate())));
		}
		return result;
	}

	@Override
	public ArrayList<MealPlannerResponse> getMealPlannerByDate(Long dateLong, Long userId) {
		String dateDate = DateTimeUtil.milisecondToDateString(dateLong);
		List<Meal_planner> meal_Planners = mealPlannerRepository.findByDate(dateDate, userId);
		ArrayList<MealPlannerResponse> result = new ArrayList<>();
		for (Meal_planner meal_Planner : meal_Planners) {
			result.add(new MealPlannerResponse(meal_Planner.getMealPlannerId(), RecipeMapper.INSTANCE.recipeToRecipeDto(meal_Planner.getRecipe()), meal_Planner.getMealType().name(), DateTimeUtil.dateToMilisecond(meal_Planner.getDate())));
		}
		return result;
	}

	@Override
	public void createNewMealPlanner(MealPlannerRequest request, Long userId) {
		Date date = DateTimeUtil.milisecondToDate(request.getDate());
		Recipe recipe = recipeService.getRecipeById(request.getRecipeId());
		if(recipe.getUser().getUserId() != userId) {
			throw new ForbiddenExeption("this is not your recipe");
		}
		MealType mealType = request.getMealType();
		
		mealPlannerRepository.save(new Meal_planner(null, recipe, recipe.getUser(), mealType, date));
	}

	@Override
	public Meal_planner getOneById(Long mealPlannerId) {
		return mealPlannerRepository.findById(mealPlannerId).orElseThrow(() -> new NotFoundExeption("this planner is not existed"));
	}

	@Override
	public void deleteById(Long mealPlannerId) {
		mealPlannerRepository.deleteById(mealPlannerId);
	}
}
