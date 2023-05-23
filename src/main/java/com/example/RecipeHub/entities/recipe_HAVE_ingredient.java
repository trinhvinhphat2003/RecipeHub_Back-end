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
@Table(name = "recipe_HAVE_ingredient")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Recipe_HAVE_Ingredient {
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

	public Recipe_HAVE_Ingredient(Recipe recipe, Ingredient ingredient, String amount) {
		super();
		this.recipe = recipe;
		this.ingredient = ingredient;
		this.amount = amount;
	}

	
}
