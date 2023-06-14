package com.example.RecipeHub.dtos;

import java.util.ArrayList;

import com.example.RecipeHub.entities.Ingredient;

public class FIlterDTO {
	private ArrayList<String> tags = new ArrayList<>();
	private ArrayList<String> ingredients;
	private boolean favorite;
	private String sortBy;
	private String direction;
	private String title;
	private String privacyStatus;
	
	public String getDirection() {
		return direction;
	}
	public void setDirection(String direction) {
		this.direction = direction;
	}
	public String getPrivacyStatus() {
		return privacyStatus;
	}
	public void setPrivacyStatus(String privacyStatus) {
		this.privacyStatus = privacyStatus;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public ArrayList<String> getTags() {
		return tags;
	}
	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}
	public ArrayList<String> getIngredients() {
		return ingredients;
	}
	public void setIngredients(ArrayList<String> ingredients) {
		this.ingredients = ingredients;
	}
	public boolean isFavorite() {
		return favorite;
	}
	public void setFavorite(boolean favorite) {
		this.favorite = favorite;
	}
	public String getSortBy() {
		return sortBy;
	}
	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}
	
	public FIlterDTO() {
		super();
	}
	public FIlterDTO(ArrayList<String> tags, ArrayList<String> ingredients, boolean favorite, String sortBy,
			String direction, String title, String privacyStatus) {
		super();
		this.tags = tags;
		this.ingredients = ingredients;
		this.favorite = favorite;
		this.sortBy = sortBy;
		this.direction = direction;
		this.title = title;
		this.privacyStatus = privacyStatus;
	}
	
	
}
