package com.example.RecipeHub.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.RecipeHub.entities.Ingredient;
import com.example.RecipeHub.entities.Recipe;
import com.example.RecipeHub.entities.Tag;
import com.example.RecipeHub.entities.User;

public interface RecipeRepository extends JpaRepository<Recipe, Long>{
	public List<Recipe> findAllByUser(User user);
	
	Optional<Recipe> findByTitle(String title);
	
	@Query("SELECT r FROM Recipe r " +
	           "JOIN r.tags t " +
	           "JOIN r.ingredients i " +
	           "WHERE t.tagName IN :tags " +
	           "AND i.ingredient.ingredientName IN :ingredients " +
	           "AND r.is_favourite = :favorite " +
	           "GROUP BY r " +
	           "HAVING COUNT(DISTINCT t) >= :tagCount " +
	           "AND COUNT(DISTINCT i) >= :ingredientCount " +
	           "ORDER BY " +
	           "CASE :sortBy " +
	           "   WHEN 'cook_time' THEN r.cook_time " +
	           "   WHEN 'rating' THEN r.rating " +
	           "   WHEN 'title' THEN r.title " +
	           "END ASC")
	    Page<Recipe> findByTagsAndIngredientsAndFavoriteOrderBy(@Param("tags") List<Tag> tags,
	                                                            @Param("ingredients") List<Ingredient> ingredients,
	                                                            @Param("favorite") boolean favorite,
	                                                            @Param("tagCount") long tagCount,
	                                                            @Param("ingredientCount") long ingredientCount,
	                                                            @Param("sortBy") String sortBy,
	                                                            Pageable pageable);
}
