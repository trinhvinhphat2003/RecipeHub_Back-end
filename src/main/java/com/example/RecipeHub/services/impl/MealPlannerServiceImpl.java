package com.example.RecipeHub.services.impl;

import com.example.RecipeHub.dtos.MealPlannerDto;
import com.example.RecipeHub.mappers.MealPlannerMapper;
import com.example.RecipeHub.repositories.MealPlannerRepository;
import com.example.RecipeHub.services.MealPlannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MealPlannerServiceImpl implements MealPlannerService {

    private final MealPlannerMapper mealPlannerMapper;
    private final MealPlannerRepository mealPlannerRepository;

    @Override
    public MealPlannerDto addMealPlanner(MealPlannerDto mealPlannerDto) {
        
        return null;
    }
}
