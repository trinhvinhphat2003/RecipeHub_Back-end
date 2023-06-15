package com.example.RecipeHub.entities;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Image {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "image_id")
	private Long imageId;
	
	@Column(name = "image_url")
	private String imageUrl;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "recipe_id")
	private Recipe recipe;
	

	public Image(Long imageId, String imageUrl, Recipe recipe) {
		super();
		this.imageId = imageId;
		this.imageUrl = imageUrl;
		this.recipe = recipe;
	}

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}

	public Image() {
		super();
	}

	public Long getImageId() {
		return imageId;
	}

	public void setImageId(Long imageId) {
		this.imageId = imageId;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	
}
