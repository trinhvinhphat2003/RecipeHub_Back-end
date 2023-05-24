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
import com.example.RecipeHub.enums.PrivacyStatus;

public interface RecipeRepository extends JpaRepository<Recipe, Long>{
	public List<Recipe> findAllByUser(User user);
	
	public List<Recipe> findAllByPrivacyStatus(PrivacyStatus privacyStatus);
	
	Optional<Recipe> findByTitle(String title);
	
	@Query("SELECT r FROM Recipe r LEFT JOIN r.tags t WHERE t.tagName IN :tagNames AND r.title like %:title% AND r.is_favourite = :isFavourite AND r.privacyStatus = :privacyStatus GROUP BY r HAVING COUNT(DISTINCT t) >= :tagCount")
	    List<Recipe> findByTags(@Param("tagNames") List<String> tagNames,
//	                                                            @Param("ingredients") List<Ingredient> ingredients,
//	                                                            @Param("favorite") boolean favorite,
	                                                            @Param("tagCount") long tagCount,
	                                                            @Param("title") String title,
	                                                            @Param("isFavourite") boolean isFavourite,
	                                                            @Param("privacyStatus") PrivacyStatus privacyStatus);
//	                                                            @Param("ingredientCount") long ingredientCount,
//	                                                            Pageable pageable);
	
	@Query("SELECT r FROM Recipe r JOIN r.ingredients ri JOIN ri.ingredient i WHERE i.ingredientName IN :ingredientNames AND r.title like %:title% AND r.is_favourite = :isFavourite AND r.privacyStatus = :privacyStatus GROUP BY r HAVING COUNT(DISTINCT i) >= :ingredientCount")
    List<Recipe> findByIngredients(@Param("ingredientNames") List<String> ingredientNames,
//                                                            @Param("ingredients") List<Ingredient> ingredients,
//                                                            @Param("favorite") boolean favorite,
                                                            @Param("ingredientCount") long ingredientCount,
                                                            @Param("title") String title,
                                                            @Param("isFavourite") boolean isFavourite,
                                                            @Param("privacyStatus") PrivacyStatus privacyStatus);
//                                                            @Param("ingredientCount") long ingredientCount,
//                                                            Pageable pageable);
	
	@Query("SELECT r FROM Recipe r JOIN r.tags t JOIN r.ingredients ri JOIN ri.ingredient i WHERE t.tagName IN :tagNames AND i.ingredientName IN :ingredientNames AND r.title like %:title% AND r.is_favourite = :isFavourite AND r.privacyStatus = :privacyStatus GROUP BY r HAVING COUNT(DISTINCT t) >= :tagCount AND COUNT(DISTINCT i) >= :ingredientCount")
    List<Recipe> findByTagsAndIngredients(@Param("tagNames") List<String> tagNames,
//                                                            @Param("ingredients") List<Ingredient> ingredients,
//                                                            @Param("favorite") boolean favorite,
                                                            @Param("tagCount") long tagCount,
                                                            @Param("ingredientNames") List<String> ingredientNames,
                                                            @Param("ingredientCount") long ingredientCount,
                                                            @Param("title") String title,
                                                            @Param("isFavourite") boolean isFavourite,
                                                            @Param("privacyStatus") PrivacyStatus privacyStatus);
//                                                            @Param("ingredientCount") long ingredientCount,
//                                                            Pageable pageable);
}
