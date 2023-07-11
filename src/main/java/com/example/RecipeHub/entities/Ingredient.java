package com.example.RecipeHub.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
	
	@ManyToOne
	@JoinColumn(name = "recipe_id")
	private Recipe recipe;

	@Column(name = "ingredient_name", nullable = false)
	private String ingredientName;

	@Column(name = "amount")
	private String amount;

	

	public Ingredient(Long ingredientId, Recipe recipe, String ingredientName, String amount) {
		super();
		this.ingredientId = ingredientId;
		this.recipe = recipe;
		this.ingredientName = ingredientName;
		this.amount = amount;
	}

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getIngredientName() {
		return ingredientName;
	}

	public void setIngredientName(String ingredientName) {
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
