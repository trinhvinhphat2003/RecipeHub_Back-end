package com.example.RecipeHub.services.impl;

import com.example.RecipeHub.dtos.request.MealPlannerDayRangeRequest;
import com.example.RecipeHub.dtos.request.MealPlannerRequest;
import com.example.RecipeHub.dtos.response.MealPlannerResponse;
import com.example.RecipeHub.entities.MealPlanner;
import com.example.RecipeHub.mappers.MealPlannerMapper;
import com.example.RecipeHub.repositories.MealPlannerRepository;
import com.example.RecipeHub.services.MealPlannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.scheduling.support.CronExpression;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

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
    public List<MealPlannerResponse> getMealPlannerInDayRange(MealPlannerDayRangeRequest mealPlannerRequest) throws Exception{
        List<MealPlanner> mealPlanner = mealPlannerRepository.findMealPlannerByUser_UserIdAndDateBeforeAndDateAfter(mealPlannerRequest.getUserId(), mealPlannerRequest.getBeforeDate(), mealPlannerRequest.getAfterDate());
        return mealPlannerMapper.mealPlannersToMealPlannerResponses(mealPlanner);
    }

    @Override
    public MealPlannerResponse updateMealPlanner(MealPlannerRequest mealPlannerRequest) throws Exception {
        MealPlanner mealPlanner = mealPlannerRepository.findById(mealPlannerRequest.getMealPlannerId()).orElseThrow(() -> new RuntimeException("Meal planner not found"));
//        mealPlannerRepository
        return null;
    }

    @Scheduled(cron = "0 0 1 15 */3 *", zone = "Asia/Saigon") // 01:00 AM on 15/1, 15/4, 15/7, 15/10
    public void removeMealPlanner100DaysBeforeCurrentDate(){
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, -90);
        Date date = calendar.getTime();
        mealPlannerRepository.deleteByDateBefore(date);
    }
}
