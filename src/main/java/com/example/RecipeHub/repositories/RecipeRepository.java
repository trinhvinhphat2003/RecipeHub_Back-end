package com.example.RecipeHub.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.RecipeHub.entities.Recipe;
import com.example.RecipeHub.entities.User;

public interface RecipeRepository extends JpaRepository<Recipe, Long>{
	public List<Recipe> findAllByUser(User user);
}
