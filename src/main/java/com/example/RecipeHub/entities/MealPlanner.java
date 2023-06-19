package com.example.RecipeHub.entities;

import java.util.ArrayList;
import java.util.Date;
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
import jakarta.persistence.Table;

@Entity
@Table(name = "meal_planner")
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
public class MealPlanner {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "meal_planner_id")
	private Long mealPlannerId;

//	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.DETACH)
	@JoinTable(name = "planner_HAVE_recipe", joinColumns = @JoinColumn(name = "meal_planner_id"), inverseJoinColumns = @JoinColumn(name = "recipe_id"))
	private List<Recipe> recipes = new ArrayList<>();

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "date",nullable = false)
	private Date date;

	public Long getMealPlannerId() {
		return mealPlannerId;
	}

	public void setMealPlannerId(Long mealPlannerId) {
		this.mealPlannerId = mealPlannerId;
	}

	public MealPlanner(Long mealPlannerId, List<Recipe> recipes, User user, Date date) {
		super();
		this.mealPlannerId = mealPlannerId;
		this.recipes = recipes;
		this.user = user;
		this.date = date;
	}

	public List<Recipe> getRecipes() {
		return recipes;
	}

	public void setRecipes(List<Recipe> recipes) {
		this.recipes = recipes;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public MealPlanner() {
		super();
	}
	
}
