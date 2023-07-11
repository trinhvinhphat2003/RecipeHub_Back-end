package com.example.RecipeHub.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.example.RecipeHub.client.dtos.IngredientDTO;
import com.example.RecipeHub.entities.Ingredient;

@Mapper
public interface IngregientMapper {
	IngregientMapper INSTANCE =  Mappers.getMapper(IngregientMapper.class);

	IngredientDTO ingredientToIngredientDto(Ingredient ingredient);
}
