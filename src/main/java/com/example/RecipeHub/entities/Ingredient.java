package com.example.RecipeHub.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "ingredient")
public class Ingredient {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ingredient_id;

	@OneToMany(mappedBy = "ingredient")
	private List<Recipe_HAVE_Ingredient> recipes = new ArrayList<>();
//	private Long recipe_id;
	@Column(nullable = false)
	private String ingredient_name;

	public Ingredient(Long ingredient_id, List<Recipe_HAVE_Ingredient> recipes, String ingredient_name) {
		super();
		this.ingredient_id = ingredient_id;
		this.recipes = recipes;
		this.ingredient_name = ingredient_name;
	}

	public Ingredient(String ingredient_name) {
		super();
		this.ingredient_name = ingredient_name;
	}

	public Ingredient() {
		super();
	}

	public Long getIngredient_id() {
		return ingredient_id;
	}

	public void setIngredient_id(Long ingredient_id) {
		this.ingredient_id = ingredient_id;
	}

	public List<Recipe_HAVE_Ingredient> getRecipes() {
		return recipes;
	}

	public void setRecipes(List<Recipe_HAVE_Ingredient> recipes) {
		this.recipes = recipes;
	}

	public String getIngredient_name() {
		return ingredient_name;
	}

	public void setIngredient_name(String ingredient_name) {
		this.ingredient_name = ingredient_name;
	}

}
