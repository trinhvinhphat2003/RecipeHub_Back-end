package com.example.RecipeHub.dtos;

import java.util.ArrayList;

public class RecipeDTO {
	private Long recipe_id;
	private Long user_id;
	private ArrayList<IngredientDTO> ingredients;
	private String recipe_tag;
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

	public RecipeDTO(Long recipe_id, Long user_id, ArrayList<IngredientDTO> ingredients, String recipe_tag,
			String title, Integer pre_time, Integer cook_time, Integer recipe_yield, Integer rating,
			boolean is_favourite, String description, String unit, String steps, String nutrition) {
		super();
		this.recipe_id = recipe_id;
		this.user_id = user_id;
		this.ingredients = ingredients;
		this.recipe_tag = recipe_tag;
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
	}

	public RecipeDTO() {
		super();
	}

	public Long getRecipe_id() {
		return recipe_id;
	}

	public void setRecipe_id(Long recipe_id) {
		this.recipe_id = recipe_id;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

	public ArrayList<IngredientDTO> getIngredients() {
		return ingredients;
	}

	public void setIngredients(ArrayList<IngredientDTO> ingredients) {
		this.ingredients = ingredients;
	}

	public String getRecipe_tag() {
		return recipe_tag;
	}

	public void setRecipe_tag(String recipe_tag) {
		this.recipe_tag = recipe_tag;
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

}
