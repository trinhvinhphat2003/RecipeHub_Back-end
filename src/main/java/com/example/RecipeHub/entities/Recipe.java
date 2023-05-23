package com.example.RecipeHub.entities;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "recipe")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Recipe {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "recipe_id")
	private Long recipe_id;

	@Column(name = "title")
	private String title;
	
	@Column(name = "pre_time")
	private Integer pre_time;
	
	@Column(name = "cook_time")
	private Integer cook_time;
	
	@Column(name = "recipe_yield")
	private Integer recipe_yield;
	
	@Column(name = "rating")
	private Integer rating;
	
	@Column(name = "is_favourite")
	private boolean is_favourite;
	
	@Column(name = "description",columnDefinition = "text")
	private String description;
	
	@Column(name = "unit")
	private String unit;
	
	@Column(name = "steps",columnDefinition = "text")
	private String steps;
	
	@Column(name = "nutrition",columnDefinition = "text")
	private String nutrition;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "recipe", cascade = CascadeType.ALL)
	private List<Recipe_HAVE_Ingredient> ingredients = new ArrayList<>();

	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinTable(name = "recipe_HAVE_tag", joinColumns = @JoinColumn(name = "recipe_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
	private List<Tag> tags = new ArrayList<>();

	public Recipe(User user, String title, Integer pre_time, Integer cook_time, Integer recipe_yield, Integer rating,
			boolean is_favourite, String description, String unit, String steps, String nutrition) {
		super();
		this.user = user;
		this.title = title;
		this.pre_time = pre_time;
		this.cook_time = cook_time;
		this.recipe_yield = recipe_yield;
		this.rating = rating;
		this.is_favourite = is_favourite;
		this.description = description;
		this.unit = unit;
		this.steps = steps;
		this.nutrition = nutrition;
	}

	
	
}
