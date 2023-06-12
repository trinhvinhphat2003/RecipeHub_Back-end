package com.example.RecipeHub.mappers;

import com.example.RecipeHub.dtos.MealPlannerDto;
import com.example.RecipeHub.entities.MealPlanner;
import com.example.RecipeHub.entities.Recipe;
import com.example.RecipeHub.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {RecipeMapper.class})
public interface MealPlannerMapper {
    MealPlannerMapper INSTANCE = Mappers.getMapper(MealPlannerMapper.class);

    @Mapping(target = "mealPlannerId", source = "mealPlannerId")
    @Mapping(target = "date", source = "date")
    @Mapping(target = "user", source = "userId", qualifiedByName = {"mapUser"})
    @Mapping(target = "recipes", source = "recipeDtos")
    MealPlanner dtoToEntity(MealPlannerDto mealPlannerDto);

    @Mapping(target = "mealPlannerId", source = "mealPlannerId")
    @Mapping(target = "date", source = "date")
    @Mapping(target = "userId", source = "user.userId")
    @Mapping(target = "recipeDtos", source = "recipes")
    MealPlannerDto entityToDto(MealPlanner mealPlanner);

    @Named("mapUser")
    default User mapUser(long id){
        return new User(id);
    }
}
