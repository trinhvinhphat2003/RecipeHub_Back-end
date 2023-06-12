package com.example.RecipeHub.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.RecipeHub.dtos.IngredientDTO;
import com.example.RecipeHub.entities.Ingredient;

@Mapper
public interface IngregientMapper {
	IngregientMapper INSTANCE =  Mappers.getMapper(IngregientMapper.class);
	@Mapping(target = "amount", source = "amount")
	IngredientDTO ingredientToIngredientDto(Ingredient ingredient, String amount);
}
