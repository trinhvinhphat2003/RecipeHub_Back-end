package com.example.RecipeHub.services;

//<<<<<<< HEAD
//import com.example.RecipeHub.client.dtos.request.MealPlannerRequest;
//import com.example.RecipeHub.client.dtos.response.MealPlannerResponse;
//import com.example.RecipeHub.dtos.request.MealPlannerDayRangeRequest;
//import com.example.RecipeHub.dtos.request.MealPlannerRequest;
//import com.example.RecipeHub.dtos.response.MealPlannerResponse;
//
//import java.util.List;
//
//public interface MealPlannerService {
//    MealPlannerResponse addMealPlanner(MealPlannerRequest mealPlannerDto) throws Exception;
//    MealPlannerResponse getMealPlannerInADay(MealPlannerRequest mealPlannerDto) throws Exception;
//    List<MealPlannerResponse> getMealPlannerInDayRange(MealPlannerDayRangeRequest mealPlannerRequest) throws Exception;
//    MealPlannerResponse updateMealPlanner(MealPlannerRequest mealPlannerRequest) throws Exception;
//=======
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.RecipeHub.client.dtos.request.MealPlannerRequest;
import com.example.RecipeHub.client.dtos.response.MealPlannerResponse;
import com.example.RecipeHub.entities.MealPlanner;
import org.springframework.stereotype.Service;

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
		List<MealPlanner> meal_Planners = mealPlannerRepository.findByDateFromTo(DateTimeUtil.milisecondToDateString(fromLong), DateTimeUtil.milisecondToDateString(toLong));
		ArrayList<MealPlannerResponse> result = new ArrayList<>();
		for (MealPlanner meal_Planner : meal_Planners) {
			result.add(new MealPlannerResponse(meal_Planner.getMealPlannerId(), RecipeMapper.INSTANCE.recipeToRecipeDto(meal_Planner.getRecipe()), meal_Planner.getMealType().name(), DateTimeUtil.dateToMilisecond(meal_Planner.getDate())));
		}
		return result;
	}

	public ArrayList<MealPlannerResponse> getMealPlannerByDate(Long dateLong) {
		String dateDate = DateTimeUtil.milisecondToDateString(dateLong);
		List<MealPlanner> meal_Planners = mealPlannerRepository.findByDate(dateDate);
		ArrayList<MealPlannerResponse> result = new ArrayList<>();
		for (MealPlanner meal_Planner : meal_Planners) {
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
		
		mealPlannerRepository.save(new MealPlanner(null, recipe, recipe.getUser(), mealType, date));
	}

	public MealPlanner getOneById(Long mealPlannerId) {
		return mealPlannerRepository.findById(mealPlannerId).orElseThrow(() -> new NotFoundExeption("this planner is not existed"));
	}

	public void deleteById(Long mealPlannerId) {
		mealPlannerRepository.deleteById(mealPlannerId);
	}
//>>>>>>> 2f578391f0a7bdb7c5c45d3e6c9720112a4db132
}
