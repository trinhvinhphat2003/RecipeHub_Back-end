package com.example.RecipeHub.admin.dtos;

import java.util.ArrayList;

import com.example.RecipeHub.client.dtos.RecipeDTO;

public class RecipesPaginationResponse {
	private ArrayList<RecipeDTO> recipes;
	private int totalItem;
	public ArrayList<RecipeDTO> getRecipes() {
		return recipes;
	}
	public void setRecipes(ArrayList<RecipeDTO> recipes) {
		this.recipes = recipes;
	}
	public int getTotalItem() {
		return totalItem;
	}
	public void setTotalItem(int totalItem) {
		this.totalItem = totalItem;
	}
	public RecipesPaginationResponse(ArrayList<RecipeDTO> recipes, int totalItem) {
		super();
		this.recipes = recipes;
		this.totalItem = totalItem;
	}
	
	
}
