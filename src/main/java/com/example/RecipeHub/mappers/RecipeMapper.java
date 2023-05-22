package com.example.RecipeHub.mappers;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.example.RecipeHub.dtos.IngredientDTO;
import com.example.RecipeHub.dtos.RecipeDTO;
import com.example.RecipeHub.entities.Recipe;
import com.example.RecipeHub.entities.Recipe_HAVE_Ingredient;
import com.example.RecipeHub.entities.User;

@Mapper
public interface RecipeMapper {

	RecipeMapper INSTANCE = Mappers.getMapper(RecipeMapper.class);

	@Named("mapUser")
	static Long mapSender(User user) {
		return user.getUser_id();
	}

	@Named("mapIngredients")
	static ArrayList<IngredientDTO> mapIngredients(List<Recipe_HAVE_Ingredient> ingredients) {
		ArrayList<IngredientDTO> ingredientDTOs = new ArrayList<>();
		for (Recipe_HAVE_Ingredient ingredient : ingredients)
			ingredientDTOs.add(IngregientMapper.INSTANCE.ingredientToIngredientDto(ingredient.getIngredient(),
					ingredient.getAmount()));
		return ingredientDTOs;
	}

	@Mappings({ @Mapping(target = "user_id", qualifiedByName = "mapUser", source = "user"),
			@Mapping(target = "ingredients", qualifiedByName = "mapIngredients", source = "ingredients") })
	RecipeDTO recipeToRecipeDto(Recipe recipe);

//	@AfterMapping
//	@Autowired
//	static void attachAmount(Recipe_IngredientRepository recipe_IngredientRepository, Recipe recipe, @MappingTarget RecipeDTO recipeDTO) {
//		List<Ingredient> ingredients = recipe.getIngredients();
//		ArrayList<IngredientDTO> ingredientDTOs = recipeDTO.getIngredients();
//		for(IngredientDTO ingredientDTO : ingredientDTOs) {
//			ingredientDTO.setAmount(recipe_IngredientRepository.findAmountByRecipe_idAndIngredient_id(recipe.getRecipe_id(), ingredientDTO.getIngredient_id()).get());
//		}
//    }

}
