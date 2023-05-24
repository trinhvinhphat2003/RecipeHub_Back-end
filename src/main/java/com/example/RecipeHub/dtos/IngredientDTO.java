package com.example.RecipeHub.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
public class IngredientDTO {
	private Long ingredient_id;
	private String ingredientName;
	private String amount;
	public Long getIngredient_id() {
		return ingredient_id;
	}
	public void setIngredient_id(Long ingredient_id) {
		this.ingredient_id = ingredient_id;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}

	public IngredientDTO(Long ingredient_id, String ingredientName, String amount) {
		super();
		this.ingredient_id = ingredient_id;
		this.ingredientName = ingredientName;
		this.amount = amount;
	}
	public String getIngredientName() {
		return ingredientName;
	}
	public void setIngredientName(String ingredientName) {
		this.ingredientName = ingredientName;
	}
	public IngredientDTO() {
		super();
	}
	
	
}
