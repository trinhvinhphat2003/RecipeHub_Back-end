package com.example.RecipeHub.entities;

import java.util.ArrayList;
import java.util.Date;

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
public class Meal_planner {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long meal_planner_id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "recipe_id")
	private Recipe recipe;
//	private Long recipe_id;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
//	private Long user_id;
	@Column(nullable = false)
	private Date date;
}
