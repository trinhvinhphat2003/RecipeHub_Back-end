package com.example.RecipeHub.mappers;

import com.example.RecipeHub.dtos.request.MealPlannerRequest;
import com.example.RecipeHub.dtos.response.MealPlannerResponse;
import com.example.RecipeHub.dtos.RecipeDTO;
import com.example.RecipeHub.entities.MealPlanner;
import com.example.RecipeHub.entities.Recipe;
import com.example.RecipeHub.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MealPlannerMapper {
    MealPlannerMapper INSTANCE = Mappers.getMapper(MealPlannerMapper.class);

    @Mapping(target = "mealPlannerId", source = "mealPlannerId")
    @Mapping(target = "date", source = "date", dateFormat = "yyyy-MM-dd")
    @Mapping(target = "user", source = "userId", qualifiedByName = {"mapUser"})
    @Mapping(target = "recipes", source = "recipeIdList")
    MealPlanner mealPlannerRequestToMealPlanner(MealPlannerRequest mealPlannerDto);

    @Mapping(target = "mealPlannerId", source = "mealPlannerId")
    @Mapping(target = "date", source = "date")
    @Mapping(target = "recipeDtos", source = "recipes")
    MealPlannerResponse mealPlannerToMealPlannerResponse(MealPlanner mealPlanner);

    @Named("mapUser")
    default User mapUser(long id){
        return new User(id);
    }

    default Recipe mapRecipe(Long id){
        return new Recipe(id);
    }

    List<Recipe> mapRecipes(List<Long> recipeIdList);
    List<RecipeDTO> mapRecipeDtos(List<Recipe> recipes);
}
