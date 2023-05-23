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
	private String ingredient_name;
	private String amount;
	public Long getIngredient_id() {
		return ingredient_id;
	}
	public void setIngredient_id(Long ingredient_id) {
		this.ingredient_id = ingredient_id;
	}
	public String getIngredient_name() {
		return ingredient_name;
	}
	public void setIngredient_name(String ingredient_name) {
		this.ingredient_name = ingredient_name;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public IngredientDTO(Long ingredient_id, String ingredient_name, String amount) {
		super();
		this.ingredient_id = ingredient_id;
		this.ingredient_name = ingredient_name;
		this.amount = amount;
	}
	public IngredientDTO() {
		super();
	}
	
	
}
