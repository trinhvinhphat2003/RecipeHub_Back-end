package com.example.RecipeHub.mappers;

import java.util.Date;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.example.RecipeHub.client.dtos.MealPlannerResponse;
import com.example.RecipeHub.client.dtos.RecipeDTO;
import com.example.RecipeHub.entities.Meal_planner;
import com.example.RecipeHub.entities.Recipe;
import com.example.RecipeHub.utils.DateTimeUtil;

@Mapper(componentModel = "spring")
public interface MealPlannerMapper {
	
	MealPlannerMapper INSTANCE = Mappers.getMapper(MealPlannerMapper.class);
	
	@Named("mapRecipe")
	static RecipeDTO mapRecipe(Recipe recipe) {
		return RecipeMapper.INSTANCE.recipeToRecipeDto(recipe);
	}
	
	@Named("mapDate")
	static Long mapDate(Date date) {
		return DateTimeUtil.dateToMilisecond(date);
	}
	
	@Mappings({
		@Mapping(target = "recipe", source = "recipe", qualifiedByName = "mapRecipe"),
		@Mapping(target = "date", source = "date", qualifiedByName = "mapDate")
	})
	MealPlannerResponse mealPlannerTomealPlannerResponse(Meal_planner meal_Planner);

}
