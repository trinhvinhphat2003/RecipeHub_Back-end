package com.example.RecipeHub.client.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
public class IngredientDTO {
	private Long ingredientId;
	private String ingredientName;
	private String amount;
	
	public IngredientDTO(Long ingredientId, String ingredientName, String amount) {
		super();
		this.ingredientId = ingredientId;
		this.ingredientName = ingredientName;
		this.amount = amount;
	}
	public Long getIngredientId() {
		return ingredientId;
	}
	public void setIngredientId(Long ingredientId) {
		this.ingredientId = ingredientId;
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
	public IngredientDTO() {
		super();
	}
	
	
}
