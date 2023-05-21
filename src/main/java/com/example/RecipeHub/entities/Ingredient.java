package com.example.RecipeHub.entities;

import java.util.ArrayList;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "ingredient")
public class Ingredient {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long ingredient_id;
	
	@ManyToMany(mappedBy = "ingredients")
	private ArrayList<Recipe> recipes;
//	private Long recipe_id;
	@Column(nullable = false)
	private String ingredient_name;
}
