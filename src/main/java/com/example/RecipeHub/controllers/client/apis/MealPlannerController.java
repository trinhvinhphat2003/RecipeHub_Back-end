package com.example.RecipeHub.controllers.client.apis;

import java.util.ArrayList;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.RecipeHub.client.dtos.MealPlannerRequest;
import com.example.RecipeHub.client.dtos.MealPlannerResponse;
import com.example.RecipeHub.entities.User;
import com.example.RecipeHub.services.MealPlannerService;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

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
		ArrayList<MealPlannerResponse> result = mealPlannerService.getMealPlannerFromTo(from, to);
		return ResponseEntity.ok().body(result);
	}
	
	@GetMapping("user/meal-planer/{date}")
	public ResponseEntity<MealPlannerResponse> getonePlanner(@AuthenticationPrincipal User user,
			@PathVariable(value = "date", required = true) Long date) {
		MealPlannerResponse result = mealPlannerService.getMealPlannerByDate(date);
		return ResponseEntity.ok().body(result);
	}
	
	@PostMapping("user/meal-planer")
	public ResponseEntity<String> createNewPlanner(@AuthenticationPrincipal User user,
			@RequestBody MealPlannerRequest request) {
		mealPlannerService.createNewMealPlanner(request, user.getUserId());
		return ResponseEntity.ok().body("create successfully");
	}
}
