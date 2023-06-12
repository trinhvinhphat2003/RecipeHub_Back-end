package com.example.RecipeHub.repositories;

import com.example.RecipeHub.entities.MealPlanner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MealPlannerRepository extends JpaRepository<MealPlanner,Long> {
}
