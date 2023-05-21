package com.example.RecipeHub.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class recipe_HAVE_ingredient {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long recipe_HAVE_ingredient_id;
	
	@ManyToOne
	@JoinColumn(name = "recipe_id")
	private Recipe recipe;
	
	@ManyToOne
	@JoinColumn(name = "ingredient_id")
	private Ingredient ingredient;
	
	private String amount;
}
