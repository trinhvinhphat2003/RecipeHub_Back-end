package com.example.RecipeHub.mappers;

import com.example.RecipeHub.client.dtos.RecipeDTO;
import com.example.RecipeHub.client.dtos.request.MealPlannerRequest;
import com.example.RecipeHub.client.dtos.response.MealPlannerResponse;
import com.example.RecipeHub.entities.MealPlanner;
import com.example.RecipeHub.entities.Recipe;
import com.example.RecipeHub.entities.User;
import com.example.RecipeHub.utils.DateTimeUtil;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.Date;
import java.util.List;

@Mapper(componentModel = "spring")
public interface MealPlannerMapper {
    MealPlannerMapper INSTANCE = Mappers.getMapper(MealPlannerMapper.class);

//    @Mapping(target = "mealPlannerId", source = "mealPlannerId")
//    @Mapping(target = "date", source = "date", dateFormat = "yyyy-MM-dd")
//    @Mapping(target = "user", source = "userId", qualifiedByName = {"mapUser"})
//    @Mapping(target = "recipes", source = "recipeIdList")
//    MealPlanner mealPlannerRequestToMealPlanner(MealPlannerRequest mealPlannerDto);
//
//    @Mapping(target = "mealPlannerId", source = "mealPlannerId")
//    @Mapping(target = "date", source = "date")
//    @Mapping(target = "recipeDtos", source = "recipes")
//    MealPlannerResponse mealPlannerToMealPlannerResponse(MealPlanner mealPlanner);

//    @Named("mapUser")
//    default User mapUser(long id){
//        return new User(id);
//    }

//    default Recipe mapRecipe(Long id){
//        return new Recipe(id);
//    }

//    List<Recipe> mapRecipes(List<Long> recipeIdList);
//    List<RecipeDTO> mapRecipeDtos(List<Recipe> recipes);
    List<MealPlannerResponse> mealPlannersToMealPlannerResponses(List<MealPlanner> mealPlanners);
//=======
//import java.util.Date;
//
//import org.mapstruct.Mapper;
//import org.mapstruct.Mapping;
//import org.mapstruct.Mappings;
//import org.mapstruct.Named;
//import org.mapstruct.factory.Mappers;
//
//import MealPlannerResponse;
//import com.example.RecipeHub.client.dtos.RecipeDTO;
//import com.example.RecipeHub.entities.Meal_planner;
//import com.example.RecipeHub.entities.Recipe;
//import com.example.RecipeHub.utils.DateTimeUtil;
//
//@Mapper(componentModel = "spring")
//public interface MealPlannerMapper {
	
//	MealPlannerMapper INSTANCE = Mappers.getMapper(MealPlannerMapper.class);
	
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
	MealPlannerResponse mealPlannerTomealPlannerResponse(MealPlanner mealPlanner);

//>>>>>>> 2f578391f0a7bdb7c5c45d3e6c9720112a4db132
}
