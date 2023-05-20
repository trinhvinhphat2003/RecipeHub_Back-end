package com.example.RecipeHub.entities;

import java.util.ArrayList;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "recipe")
public class Recipe {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long recipe_id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
//	private Long user_id;
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "recipe_HAVE_ingredient",
			joinColumns = @JoinColumn(name = "recipe_id"),
			inverseJoinColumns = @JoinColumn(name = "ingredient_id")
			)
	private ArrayList<Ingredient> ingredients;
	
//	private Long ingredient_id;
	private String tag;
	private String title;
	private Integer pre_time;
	private Integer cook_time;
	private Integer recipe_yield;
	private Integer rating;
	private boolean is_favourite;
	@Column(columnDefinition = "text")
	private String description;
	private String unit;
	@Column(columnDefinition = "text")
	private String steps;
	@Column(columnDefinition = "text")
	private String nutrition;
}
