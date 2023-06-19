package com.example.RecipeHub.controllers.client.apis;

import com.example.RecipeHub.dtos.request.MealPlannerDayRangeRequest;
import com.example.RecipeHub.dtos.request.MealPlannerRequest;
import com.example.RecipeHub.dtos.response.MealPlannerResponse;
import com.example.RecipeHub.services.MealPlannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/v1/meal-planner")
@RequiredArgsConstructor
public class MealPlannerController {
    private final MealPlannerService mealPlannerService;
    @PostMapping(path = "/add-meal-planner")
    public ResponseEntity<MealPlannerResponse> addMealPlanner(@RequestBody MealPlannerRequest mealPlannerRequest) throws Exception{
        return ResponseEntity.ok(mealPlannerService.addMealPlanner(mealPlannerRequest));
    }
    @GetMapping(path = "/get-meal-planners-in-a-day")
    public ResponseEntity<MealPlannerResponse> getMealPlannerInADay(@RequestBody MealPlannerRequest mealPlannerRequest) throws Exception{
        return ResponseEntity.ok(mealPlannerService.getMealPlannerInADay(mealPlannerRequest));
    }
    @GetMapping(path = "/get-meal-planners-in-days-range")
    public ResponseEntity<List<MealPlannerResponse>> getMealPlannerInDaysRange(@RequestBody MealPlannerDayRangeRequest mealPlannerRequest) throws Exception{
        return ResponseEntity.ok(mealPlannerService.getMealPlannerInDayRange(mealPlannerRequest));
    }
    @PutMapping(path = "/update-meal-planner")
    public ResponseEntity<MealPlannerResponse> updateMealPlanner(@RequestBody MealPlannerRequest mealPlannerRequest) throws Exception{
        return ResponseEntity.ok(mealPlannerService.updateMealPlanner(mealPlannerRequest));
    }

}
