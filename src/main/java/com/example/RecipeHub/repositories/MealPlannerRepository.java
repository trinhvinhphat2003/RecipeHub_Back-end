package com.example.RecipeHub.repositories;

import java.util.Date;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.RecipeHub.entities.Meal_planner;

@Repository
public interface MealPlannerRepository extends JpaRepository<Meal_planner, Long>{
	Optional<Meal_planner> findByDate(Date date);
}
