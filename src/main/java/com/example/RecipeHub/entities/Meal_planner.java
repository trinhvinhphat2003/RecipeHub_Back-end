package com.example.RecipeHub.entities;

import java.util.Date;

import com.example.RecipeHub.enums.MealType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "meal_planner")
//@Data
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
public class Meal_Planner {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "meal_planner_id")
	private Long mealPlannerId;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "recipe_id")
	private Recipe recipe;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	
	@Column(name = "meal_type")
	private MealType mealType;

	@Column(name = "date",nullable = false)
	private Date date;

	public Long getMealPlannerId() {
		return mealPlannerId;
	}

	public MealType getMealType() {
		return mealType;
	}

	public void setMealType(MealType mealType) {
		this.mealType = mealType;
	}

	public void setMealPlannerId(Long mealPlannerId) {
		this.mealPlannerId = mealPlannerId;
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

	public Meal_Planner() {
		super();
	}

	public Meal_Planner(Long mealPlannerId, Recipe recipe, User user, MealType mealType, Date date) {
		super();
		this.mealPlannerId = mealPlannerId;
		this.recipe = recipe;
		this.user = user;
		this.mealType = mealType;
		this.date = date;
	}

	public Recipe getRecipe() {
		return recipe;
	}

	public void setRecipe(Recipe recipe) {
		this.recipe = recipe;
	}
	
	
	//
	
	
}
