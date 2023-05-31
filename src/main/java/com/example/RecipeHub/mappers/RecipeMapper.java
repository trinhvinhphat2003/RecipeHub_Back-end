package com.example.RecipeHub.mappers;

import java.util.ArrayList;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.example.RecipeHub.dtos.ImageDTO;
import com.example.RecipeHub.dtos.IngredientDTO;
import com.example.RecipeHub.dtos.RecipeDTO;
import com.example.RecipeHub.dtos.TagDTO;
import com.example.RecipeHub.entities.Image;
import com.example.RecipeHub.entities.Recipe;
import com.example.RecipeHub.entities.Recipe_HAVE_Ingredient;
import com.example.RecipeHub.entities.Tag;
import com.example.RecipeHub.entities.User;
import com.example.RecipeHub.enums.PrivacyStatus;

@Mapper(componentModel = "spring")
public interface RecipeMapper {

	RecipeMapper INSTANCE = Mappers.getMapper(RecipeMapper.class);

	@Named("mapUser")
	static Long mapSender(User user) {
		return user.getUserId();
	}

	@Named("mapIngredients")
	static ArrayList<IngredientDTO> mapIngredients(List<Recipe_HAVE_Ingredient> ingredients) {
		ArrayList<IngredientDTO> ingredientDTOs = new ArrayList<>();
		for (Recipe_HAVE_Ingredient ingredient : ingredients)
			ingredientDTOs.add(IngregientMapper.INSTANCE.ingredientToIngredientDto(ingredient.getIngredient(),
					ingredient.getAmount()));
		return ingredientDTOs;
	}

	@Named("mapTags")
	static ArrayList<TagDTO> mapTag(List<Tag> tags) {
		ArrayList<TagDTO> tagDTOs = new ArrayList<>();
		for (Tag tag : tags)
			tagDTOs.add(TagMapper.INSTANCE.tagToTagDto(tag));
		return tagDTOs;
	}

	@Named("mapImages")
	static ArrayList<ImageDTO> mapImages(List<Image> images) {
		ArrayList<ImageDTO> imageDTOs = new ArrayList<>();
		for (Image image : images)
			imageDTOs.add(ImageMapper.INSTANCE.imageToImageDto(image));
		return imageDTOs;
	}
	@Named("mapPrivacyStatus")
	static String mapPrivacyStatus(PrivacyStatus privacyStatus) {
		return privacyStatus.name();
	}

	@Mappings({ @Mapping(target = "userId", qualifiedByName = "mapUser", source = "user"),
			@Mapping(target = "ingredients", qualifiedByName = "mapIngredients", source = "ingredients"),
			@Mapping(target = "images", qualifiedByName = "mapImages", source = "images") ,
			@Mapping(target = "tags", qualifiedByName = "mapTags", source = "tags"),
			@Mapping(target = "privacyStatus", qualifiedByName = "mapPrivacyStatus", source = "privacyStatus")
			})
	RecipeDTO recipeToRecipeDto(Recipe recipe);

//	@AfterMapping

}
