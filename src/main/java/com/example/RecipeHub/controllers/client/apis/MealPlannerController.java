package com.example.RecipeHub.controllers.client.apis;

import java.util.ArrayList;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.RecipeHub.dtos.MealPlannerRequest;
import com.example.RecipeHub.dtos.MealPlannerResponse;
import com.example.RecipeHub.entities.Meal_planner;
import com.example.RecipeHub.entities.User;
import com.example.RecipeHub.errorHandlers.ForbiddenExeption;
import com.example.RecipeHub.services.MealPlannerService;

@RestController
@RequestMapping("/api/v1/")
public class MealPlannerController {
	
	private final MealPlannerService mealPlannerService;
	
	public MealPlannerController(MealPlannerService mealPlannerService) {
		super();
		this.mealPlannerService = mealPlannerService;
	}

	@GetMapping("user/meal-planers")
	public ResponseEntity<ArrayList<MealPlannerResponse>> getAllPlannerFromTo(@AuthenticationPrincipal User user,
			@RequestParam(value = "from", required = true) Long from,
			@RequestParam(value = "to", required = true) Long to) {
		ArrayList<MealPlannerResponse> result = mealPlannerService.getMealPlannerFromTo(from, to, user.getUserId());
		return ResponseEntity.ok().body(result);
	}
	
	@GetMapping("user/meal-planers/{date}")
	public ResponseEntity< ArrayList<MealPlannerResponse>> getonePlanner(@AuthenticationPrincipal User user,
			@PathVariable(value = "date", required = true) Long date) {
		ArrayList<MealPlannerResponse> result = mealPlannerService.getMealPlannerByDate(date, user.getUserId());
		return ResponseEntity.ok().body(result);
	}
	
	@DeleteMapping("user/meal-planer/{mealPlannerId}")
	public ResponseEntity<String> deleteById(@AuthenticationPrincipal User user, @PathVariable("mealPlannerId") Long mealPlannerId) {
		Meal_planner meal_planner = mealPlannerService.getOneById(mealPlannerId);
		if(meal_planner.getUser().getUserId() != user.getUserId()) throw new ForbiddenExeption("this planner is not your");
		mealPlannerService.deleteById(meal_planner.getMealPlannerId());
		return ResponseEntity.ok("this planner have been delete");
	}
	
	@PostMapping("user/meal-planer")
	public ResponseEntity<String> postNewPlanner(@AuthenticationPrincipal User user, @RequestBody MealPlannerRequest request) {
		mealPlannerService.createNewMealPlanner(request, user.getUserId());
		return ResponseEntity.ok("this planner has been created successfully");
	}

}
