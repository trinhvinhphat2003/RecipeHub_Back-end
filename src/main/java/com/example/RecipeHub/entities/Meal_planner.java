package com.example.RecipeHub.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
@Table(name = "meal_planner")
public class Meal_planner {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long meal_planner_id;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "planner_HAVE_recipe", joinColumns = @JoinColumn(name = "meal_planner_id"), inverseJoinColumns = @JoinColumn(name = "recipe_id"))
	private List<Recipe> recipes = new ArrayList<>();
//	private Long recipe_id;

	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
//	private Long user_id;
	@Column(nullable = false)
	private Date date;
}
