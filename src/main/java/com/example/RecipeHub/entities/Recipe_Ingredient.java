package com.example.RecipeHub.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Recipe_HAVE_Ingredient")
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
public class Recipe_Ingredient {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long recipe_HAVE_ingredient_id;

	@ManyToOne
	@JoinColumn(name = "recipe_id")
	private Recipe recipe;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "ingredient_id")
	private Ingredient ingredient;

	@Column(name = "amount")
	private String amount;

	public Recipe_Ingredient(Recipe recipe, Ingredient ingredient, String amount) {
		super();
		this.recipe = recipe;
		this.ingredient = ingredient;
		this.amount = amount;
	}

	public Long getRecipe_HAVE_ingredient_id() {
		return recipe_HAVE_ingredient_id;
	}

	public void setRecipe_HAVE_ingredient_id(Long recipe_HAVE_ingredient_id) {
		this.recipe_HAVE_ingredient_id = recipe_HAVE_ingredient_id;
	}

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

	public Ingredient getIngredient() {
		return ingredient;
	}

	public void setIngredient(Ingredient ingredient) {
		this.ingredient = ingredient;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public Recipe_Ingredient(Long recipe_HAVE_ingredient_id, Recipe recipe, Ingredient ingredient, String amount) {
		super();
		this.recipe_HAVE_ingredient_id = recipe_HAVE_ingredient_id;
		this.recipe = recipe;
		this.ingredient = ingredient;
		this.amount = amount;
	}

	public Recipe_Ingredient() {
		super();
	}

	
}
