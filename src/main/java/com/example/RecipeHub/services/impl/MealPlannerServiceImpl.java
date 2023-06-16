package com.example.RecipeHub.services.impl;

import com.example.RecipeHub.dtos.request.MealPlannerDayRangeRequest;
import com.example.RecipeHub.dtos.request.MealPlannerRequest;
import com.example.RecipeHub.dtos.response.MealPlannerResponse;
import com.example.RecipeHub.entities.MealPlanner;
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
    public MealPlannerResponse addMealPlanner(MealPlannerRequest mealPlannerRequest) throws Exception {
        MealPlanner mealPlanner = mealPlannerMapper.mealPlannerRequestToMealPlanner(mealPlannerRequest);
        MealPlanner mealPlannerInDatabase = mealPlannerRepository.findMealPlannerByDateAndUser_UserId(mealPlannerRequest.getDate(), mealPlannerRequest.getUserId());
        MealPlanner result;
        MealPlannerResponse response;
        if(mealPlannerInDatabase == null){
            result = mealPlannerRepository.save(mealPlanner);
        } else {
            mealPlannerInDatabase.getRecipes().addAll(mealPlanner.getRecipes());
            result = mealPlannerRepository.save(mealPlannerInDatabase);
        }
        response = mealPlannerMapper.mealPlannerToMealPlannerResponse(result);
        return response;
    }

    @Override
    public MealPlannerResponse getMealPlannerInADay(MealPlannerRequest mealPlannerRequest) throws Exception {
        MealPlanner mealPlanner = mealPlannerRepository.findMealPlannerByDateAndUser_UserId(mealPlannerRequest.getDate(), mealPlannerRequest.getUserId());
        MealPlannerResponse response = mealPlannerMapper.mealPlannerToMealPlannerResponse(mealPlanner);
        return response;
    }

    @Override
    public MealPlannerResponse getMealPlannerInDayRange(MealPlannerDayRangeRequest mealPlannerRequest) throws Exception{
        MealPlanner mealPlanner = mealPlannerRepository.findMealPlannerByUser_UserIdAndDateBeforeAndDateAfter(mealPlannerRequest.getUserId(), mealPlannerRequest.getBeforeDate(), mealPlannerRequest.getAfterDate());
        return mealPlannerMapper.mealPlannerToMealPlannerResponse(mealPlanner);
    }

    @Override
    public MealPlannerResponse updateMealPlanner(MealPlannerRequest mealPlannerRequest) throws Exception {
        MealPlanner mealPlanner = mealPlannerRepository.findById(mealPlannerRequest.getMealPlannerId()).orElseThrow(() -> new RuntimeException("Meal planner not found"));
//        mealPlanner.
        return null;
    }
}
