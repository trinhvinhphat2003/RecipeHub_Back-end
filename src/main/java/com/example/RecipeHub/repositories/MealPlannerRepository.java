package com.example.RecipeHub.repositories;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.RecipeHub.entities.Meal_planner;

@Repository
public interface MealPlannerRepository extends JpaRepository<Meal_planner, Long>{
	
	@Query(value = "SELECT * FROM meal_planner m where date(m.date) = :date", nativeQuery = true)
	List<Meal_planner> findByDate(@Param("date") String date);
	@Query(value = "SELECT * FROM meal_planner m where date(m.date) between :from and :to", nativeQuery = true)
	List<Meal_planner> findByDateFromTo(@Param("from") String from,@Param("to") String to);
}
