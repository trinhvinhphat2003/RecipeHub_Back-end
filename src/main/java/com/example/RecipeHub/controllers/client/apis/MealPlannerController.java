package com.example.RecipeHub.controllers.client.apis;

//import com.example.RecipeHub.client.dtos.response.MealPlannerResponse;
//import com.example.RecipeHub.dtos.request.MealPlannerDayRangeRequest;
//import com.example.RecipeHub.client.dtos.response.MealPlannerResponse;
//import com.example.RecipeHub.services.MealPlannerService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.ArrayList;
//import java.util.List;
//
//@RestController
//@RequestMapping(path = "/api/v1/meal-planner")
//public class MealPlannerController {
//    private final MealPlannerService mealPlannerService;
//
//	public MealPlannerController(MealPlannerService mealPlannerService) {
//		this.mealPlannerService = mealPlannerService;
//	}
//
//	@PostMapping(path = "/add-meal-planner")
//    public ResponseEntity<MealPlannerResponse> addMealPlanner(@RequestBody MealPlannerRequest mealPlannerRequest) throws Exception{
//        return ResponseEntity.ok(mealPlannerService.addMealPlanner(mealPlannerRequest));
//    }
//    @GetMapping(path = "/get-meal-planners-in-a-day")
//    public ResponseEntity<MealPlannerResponse> getMealPlannerInADay(@RequestBody MealPlannerRequest mealPlannerRequest) throws Exception{
//        return ResponseEntity.ok(mealPlannerService.getMealPlannerInADay(mealPlannerRequest));
//    }
//    @GetMapping(path = "/get-meal-planners-in-days-range")
//    public ResponseEntity<List<MealPlannerResponse>> getMealPlannerInDaysRange(@RequestBody MealPlannerDayRangeRequest mealPlannerRequest) throws Exception{
//        return ResponseEntity.ok(mealPlannerService.getMealPlannerInDayRange(mealPlannerRequest));
//    }
//    @PutMapping(path = "/update-meal-planner")
//    public ResponseEntity<MealPlannerResponse> updateMealPlanner(@RequestBody MealPlannerRequest mealPlannerRequest) throws Exception{
//        return ResponseEntity.ok(mealPlannerService.updateMealPlanner(mealPlannerRequest));
//    }
//=======
import java.util.ArrayList;

import com.example.RecipeHub.client.dtos.response.MealPlannerResponse;
import com.example.RecipeHub.entities.MealPlanner;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.RecipeHub.client.dtos.request.MealPlannerRequest;
import com.example.RecipeHub.entities.User;
import com.example.RecipeHub.errorHandlers.ForbiddenExeption;
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
	
	@GetMapping("user/meal-planers/{date}")
	public ResponseEntity< ArrayList<MealPlannerResponse>> getonePlanner(@AuthenticationPrincipal User user,
			@PathVariable(value = "date", required = true) Long date) {
		ArrayList<MealPlannerResponse> result = mealPlannerService.getMealPlannerByDate(date);
		return ResponseEntity.ok().body(result);
	}
	
	@DeleteMapping("user/meal-planer/{mealPlannerId}")
	public ResponseEntity<String> deleteById(@AuthenticationPrincipal User user, @PathVariable("mealPlannerId") Long mealPlannerId) {
		MealPlanner meal_planner = mealPlannerService.getOneById(mealPlannerId);
		if(meal_planner.getUser().getUserId() != user.getUserId()) throw new ForbiddenExeption("this planner is not your");
		mealPlannerService.deleteById(meal_planner.getMealPlannerId());
		return ResponseEntity.ok("this planner have been delete");
	}
	
	@PostMapping("user/meal-planer")
	public ResponseEntity<String> postNewPlanner(@AuthenticationPrincipal User user, @RequestBody MealPlannerRequest request) {
		mealPlannerService.createNewMealPlanner(request, user.getUserId());
		return ResponseEntity.ok("this planner has been created successfully");
	}
//>>>>>>> 2f578391f0a7bdb7c5c45d3e6c9720112a4db132
}
