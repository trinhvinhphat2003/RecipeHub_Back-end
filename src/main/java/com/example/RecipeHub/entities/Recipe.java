package com.example.RecipeHub.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "recipe")
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
public class Recipe {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "recipe_id")
	private Long recipe_id;

	@Column(name = "title")
	private String title;
	
	@Column(name = "pre_time")
	private Integer pre_time;
	
	@Column(name = "cook_time")
	private Integer cook_time;
	
	@Column(name = "recipe_yield")
	private Integer recipe_yield;
	
	@Column(name = "rating")
	private Integer rating;
	
	@Column(name = "is_favourite")
	private boolean is_favourite;
	
	@Column(name = "description",columnDefinition = "text")
	private String description;
	
	@Column(name = "unit")
	private String unit;
	
	@Column(name = "steps",columnDefinition = "text")
	private String steps;
	
	@Column(name = "nutrition",columnDefinition = "text")
	private String nutrition;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "recipe", cascade = CascadeType.ALL)
	private List<Recipe_HAVE_Ingredient> ingredients = new ArrayList<>();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "recipe_HAVE_tag", joinColumns = @JoinColumn(name = "recipe_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
	private List<Tag> tags = new ArrayList<>();

	public Recipe(User user, String title, Integer pre_time, Integer cook_time, Integer recipe_yield, Integer rating,
			boolean is_favourite, String description, String unit, String steps, String nutrition) {
		super();
		this.user = user;
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

	public Long getRecipe_id() {
		return recipe_id;
	}

	public void setRecipe_id(Long recipe_id) {
		this.recipe_id = recipe_id;
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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Recipe_HAVE_Ingredient> getIngredients() {
		return ingredients;
	}

	public void setIngredients(List<Recipe_HAVE_Ingredient> ingredients) {
		this.ingredients = ingredients;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public Recipe(Long recipe_id, String title, Integer pre_time, Integer cook_time, Integer recipe_yield,
			Integer rating, boolean is_favourite, String description, String unit, String steps, String nutrition,
			User user, List<Recipe_HAVE_Ingredient> ingredients, List<Tag> tags) {
		super();
		this.recipe_id = recipe_id;
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
		this.user = user;
		this.ingredients = ingredients;
		this.tags = tags;
	}

	public Recipe() {
		super();
	}

	//
	
	
	
}
