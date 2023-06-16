package com.example.RecipeHub.services;

import com.example.RecipeHub.dtos.request.MealPlannerDayRangeRequest;
import com.example.RecipeHub.dtos.request.MealPlannerRequest;
import com.example.RecipeHub.dtos.response.MealPlannerResponse;

import java.util.Date;

public interface MealPlannerService {
    MealPlannerResponse addMealPlanner(MealPlannerRequest mealPlannerDto) throws Exception;
    MealPlannerResponse getMealPlannerInADay(MealPlannerRequest mealPlannerDto) throws Exception;
    MealPlannerResponse getMealPlannerInDayRange(MealPlannerDayRangeRequest mealPlannerRequest) throws Exception;
    MealPlannerResponse updateMealPlanner(MealPlannerRequest mealPlannerRequest) throws Exception;
}
