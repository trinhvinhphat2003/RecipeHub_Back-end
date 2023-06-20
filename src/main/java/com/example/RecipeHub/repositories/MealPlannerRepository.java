package com.example.RecipeHub.repositories;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.RecipeHub.entities.Meal_Planner;

@Repository
public interface MealPlannerRepository extends JpaRepository<Meal_Planner, Long>{
	Optional<Meal_Planner> findByDate(Date date);
}
