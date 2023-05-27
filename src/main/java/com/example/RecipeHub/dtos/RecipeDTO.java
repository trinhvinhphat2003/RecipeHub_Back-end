package com.example.RecipeHub.dtos;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
public class RecipeDTO {
	private Long recipe_id;
	private Long userId;
	private ArrayList<IngredientDTO> ingredients;
	private ArrayList<ImageDTO> images;
	private ArrayList<TagDTO> tags;
	private String title;
	private Integer pre_time;
	private Integer cook_time;
	private Integer recipe_yield;
	private Integer rating;
	private boolean is_favourite;
	private String description;
	private String unit;
	private String steps;
	private String nutrition;
	private String privacyStatus;
	public String getPrivacyStatus() {
		return privacyStatus;
	}
	public void setPrivacyStatus(String privacyStatus) {
		this.privacyStatus = privacyStatus;
	}
	public Long getRecipe_id() {
		return recipe_id;
	}
	public void setRecipe_id(Long recipe_id) {
		this.recipe_id = recipe_id;
	}
	public ArrayList<IngredientDTO> getIngredients() {
		return ingredients;
	}
	public void setIngredients(ArrayList<IngredientDTO> ingredients) {
		this.ingredients = ingredients;
	}
	public ArrayList<TagDTO> getTags() {
		return tags;
	}
	public void setTags(ArrayList<TagDTO> tags) {
		this.tags = tags;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public Integer getPre_time() {
		return pre_time;
	}
	public void setPre_time(Integer pre_time) {
		this.pre_time = pre_time;
	}
	public Integer getCook_time() {
		return cook_time;
	}
	public void setCook_time(Integer cook_time) {
		this.cook_time = cook_time;
	}
	public Integer getRecipe_yield() {
		return recipe_yield;
	}
	public void setRecipe_yield(Integer recipe_yield) {
		this.recipe_yield = recipe_yield;
	}
	public Integer getRating() {
		return rating;
	}
	public void setRating(Integer rating) {
		this.rating = rating;
	}
	public boolean isIs_favourite() {
		return is_favourite;
	}
	public void setIs_favourite(boolean is_favourite) {
		this.is_favourite = is_favourite;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUnit() {
		return unit;
	}
	public void setUnit(String unit) {
		this.unit = unit;
	}
	public String getSteps() {
		return steps;
	}
	public void setSteps(String steps) {
		this.steps = steps;
	}
	public String getNutrition() {
		return nutrition;
	}
	public void setNutrition(String nutrition) {
		this.nutrition = nutrition;
	}
	
	public ArrayList<ImageDTO> getImages() {
		return images;
	}
	public void setImages(ArrayList<ImageDTO> images) {
		this.images = images;
	}
	

	public RecipeDTO(Long recipe_id, Long userId, ArrayList<IngredientDTO> ingredients, ArrayList<ImageDTO> images,
			ArrayList<TagDTO> tags, String title, Integer pre_time, Integer cook_time, Integer recipe_yield,
			Integer rating, boolean is_favourite, String description, String unit, String steps, String nutrition,
			String privacyStatus) {
		super();
		this.recipe_id = recipe_id;
		this.userId = userId;
		this.ingredients = ingredients;
		this.images = images;
		this.tags = tags;
		this.title = title;
		this.pre_time = pre_time;
		this.cook_time = cook_time;
		this.recipe_yield = recipe_yield;
		this.rating = rating;
		this.is_favourite = is_favourite;
		this.description = description;
		this.unit = unit;
		this.steps = steps;
		this.nutrition = nutrition;
		this.privacyStatus = privacyStatus;
	}
	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public RecipeDTO() {
		super();
	}
	
	
}
