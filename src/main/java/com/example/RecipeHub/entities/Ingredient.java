package com.example.RecipeHub.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "ingredient")
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
public class Ingredient {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ingredient_id")
	private Long ingredientId;

	@OneToMany(mappedBy = "ingredient", fetch = FetchType.LAZY)
	private List<Recipe_HAVE_Ingredient> recipes = new ArrayList<>();

	@Column(name = "ingredient_name", nullable = false)
	private String ingredientName;

	public Ingredient(String ingredientName) {
		super();
		this.ingredientName = ingredientName;
	}
	
	public List<Recipe_HAVE_Ingredient> getRecipes() {
		return recipes;
	}

	public void setRecipes(List<Recipe_HAVE_Ingredient> recipes) {
		this.recipes = recipes;
	}

	public String getIngredientName() {
		return ingredientName;
	}

	public void setIngredientName(String ingredientName) {
		this.ingredientName = ingredientName;
	}


	public Ingredient(Long ingredientId, List<Recipe_HAVE_Ingredient> recipes, String ingredientName) {
		super();
		this.ingredientId = ingredientId;
		this.recipes = recipes;
		this.ingredientName = ingredientName;
	}

	public Long getIngredientId() {
		return ingredientId;
	}

	public void setIngredientId(Long ingredientId) {
		this.ingredientId = ingredientId;
	}

	public Ingredient() {
		super();
	}
	
	//
	
	
}
