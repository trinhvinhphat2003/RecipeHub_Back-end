package com.example.RecipeHub.dtos;

import java.util.ArrayList;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RecipeDTO {
	private Long recipe_id;
	private Long user_id;
	private ArrayList<IngredientDTO> ingredients;
	private ArrayList<TagDTO> tags;
	private String title;
	private Integer pre_time;
	private Integer cook_time;
	private Integer recipe_yield;
	private Integer rating;
	private boolean is_favourite;
	private String description;
	private String unit;
	private String steps;
	private String nutrition;
}
