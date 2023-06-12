package com.example.RecipeHub.controllers.client.apis;

import com.example.RecipeHub.dtos.MealPlannerDto;
import com.example.RecipeHub.services.MealPlannerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/meal-planner")
@RequiredArgsConstructor
public class MealPlannerController {
    private final MealPlannerService mealPlannerService;
    @PostMapping(path = "/add-meal-planner")
    public ResponseEntity<MealPlannerDto> addMealPlanner(@RequestBody MealPlannerDto mealPlannerDto){
        return ResponseEntity.ok(mealPlannerService.addMealPlanner(mealPlannerDto));
    }
//    @GetMapping(path = "/get-meal-planners-in-a-day")
//    public ResponseEntity<MealPlannerDto> getMealPlannerInADay(){
//        return ResponseEntity.ok(mealPlannerService);
//    }
    //@GetMapping(path = "/get-meal-planners-in-days-range")
    //@PutMapping(path = "/update-meal-planner")
    //@DeleteMapping(path = "/remove-meal-planner")

}
