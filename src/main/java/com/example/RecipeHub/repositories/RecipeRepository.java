package com.example.RecipeHub.repositories;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.RecipeHub.entities.Recipe;
import com.example.RecipeHub.entities.User;
import com.example.RecipeHub.enums.PrivacyStatus;

@Repository
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
	public List<Recipe> findAllByUser(User user);
	
	@Query("SELECT r FROM Recipe r WHERE (r.privacyStatus = :privacyStatus OR :privacyStatus IS NULL)")
	public Page<Recipe> findAllByPrivacyStatus(@Param("privacyStatus") PrivacyStatus privacyStatus, Pageable pageable);
	
	@Query("SELECT r FROM Recipe r WHERE (r.privacyStatus = :privacyStatus OR :privacyStatus IS NULL) AND r.title like %:title% AND r.is_favourite = :isFavourite")
	public Page<Recipe> findAllGlobalRecipeByPrivacyStatus(@Param("title") String title, @Param("isFavourite") boolean isFavourite, @Param("privacyStatus") PrivacyStatus privacyStatus, Pageable pageable);
	
	@Query("SELECT r FROM Recipe r LEFT JOIN r.user u WHERE (r.privacyStatus = :privacyStatus OR :privacyStatus IS NULL) AND r.title like %:title% AND r.is_favourite = :isFavourite AND u.userId = :userId")
	public Page<Recipe> findAllUserRecipesByPrivacyStatus(@Param("title") String title, @Param("isFavourite") boolean isFavourite, @Param("privacyStatus") PrivacyStatus privacyStatus, @Param("userId") Long userId, Pageable pageable);

	Optional<Recipe> findByTitle(String title);

	@Query("SELECT r FROM Recipe r LEFT JOIN r.tags t WHERE t.tagName IN :tagNames AND r.title like %:title% AND r.is_favourite = :isFavourite AND r.privacyStatus = :privacyStatus GROUP BY r HAVING COUNT(DISTINCT t) >= :tagCount")
	Page<Recipe> findByTags(@Param("tagNames") List<String> tagNames, @Param("tagCount") long tagCount,
			@Param("title") String title, @Param("isFavourite") boolean isFavourite,
			@Param("privacyStatus") PrivacyStatus privacyStatus, Pageable pageable);

	@Query("SELECT r FROM Recipe r LEFT JOIN r.ingredients ri WHERE ri.ingredient.ingredientName IN :ingredientNames AND r.title like %:title% AND r.is_favourite = :isFavourite AND r.privacyStatus = :privacyStatus GROUP BY r HAVING COUNT(DISTINCT ri) >= :ingredientCount")
	Page<Recipe> findByIngredients(@Param("ingredientNames") List<String> ingredientNames,
			@Param("ingredientCount") long ingredientCount, @Param("title") String title,
			@Param("isFavourite") boolean isFavourite, @Param("privacyStatus") PrivacyStatus privacyStatus, Pageable pageable);

	@Query("SELECT r FROM Recipe r JOIN r.tags t JOIN r.ingredients ri WHERE t.tagName IN :tagNames AND ri.ingredient.ingredientName IN :ingredientNames AND r.title like %:title% AND r.is_favourite = :isFavourite AND (r.privacyStatus = :privacyStatus OR :privacyStatus IS NULL) GROUP BY r HAVING COUNT(DISTINCT t) >= :tagCount AND COUNT(DISTINCT ri) >= :ingredientCount")
	Page<Recipe> findByTagsAndIngredients(@Param("tagNames") List<String> tagNames, @Param("tagCount") long tagCount,
			@Param("ingredientNames") List<String> ingredientNames, @Param("ingredientCount") long ingredientCount,
			@Param("title") String title, @Param("isFavourite") boolean isFavourite,
			@Param("privacyStatus") PrivacyStatus privacyStatus, Pageable pageable);
	
//	@Query("SELECT r FROM Recipe r JOIN r.tags t JOIN r.ingredients ri JOIN ri.ingredient i WHERE (t.tagName IN :tagNames OR :tagNames IS NULL) AND (i.ingredientName IN :ingredientNames OR :ingredientNames IS NULL) AND r.title like %:title% AND r.is_favourite = :isFavourite AND (r.privacyStatus = :privacyStatus OR :privacyStatus IS NULL) GROUP BY r HAVING COUNT(DISTINCT t) >= :tagCount AND COUNT(DISTINCT i) >= :ingredientCount")
//	List<Recipe> findByTagsAndIngredients(@Param("tagNames") List<String> tagNames, @Param("tagCount") long tagCount,
//			@Param("ingredientNames") List<String> ingredientNames, @Param("ingredientCount") long ingredientCount,
//			@Param("title") String title, @Param("isFavourite") boolean isFavourite,
//			@Param("privacyStatus") PrivacyStatus privacyStatus);
	
	@Query("SELECT r FROM Recipe r LEFT JOIN r.tags t JOIN r.user u WHERE t.tagName IN :tagNames AND r.title like %:title% AND r.is_favourite = :isFavourite AND (r.privacyStatus = :privacyStatus OR :privacyStatus IS NULL) AND u.userId = :userId GROUP BY r HAVING COUNT(DISTINCT t) >= :tagCount")
	Page<Recipe> findByTagsAndUser(@Param("tagNames") List<String> tagNames, @Param("tagCount") long tagCount,
			@Param("title") String title, @Param("isFavourite") boolean isFavourite,
			@Param("privacyStatus") PrivacyStatus privacyStatus, @Param("userId") Long userId, Pageable pageable);

	@Query("SELECT r FROM Recipe r LEFT JOIN r.ingredients ri JOIN r.user u WHERE ri.ingredient.ingredientName IN :ingredientNames AND r.title like %:title% AND r.is_favourite = :isFavourite AND (r.privacyStatus = :privacyStatus OR :privacyStatus IS NULL) AND u.userId = :userId GROUP BY r HAVING COUNT(DISTINCT ri) >= :ingredientCount")
	Page<Recipe> findByIngredientsAndUser(@Param("ingredientNames") List<String> ingredientNames,
			@Param("ingredientCount") long ingredientCount, @Param("title") String title,
			@Param("isFavourite") boolean isFavourite, @Param("privacyStatus") PrivacyStatus privacyStatus, @Param("userId") Long userId, Pageable pageable);

	@Query("SELECT r FROM Recipe r LEFT JOIN r.tags t LEFT JOIN r.ingredients ri JOIN r.user u WHERE t.tagName IN :tagNames AND ri.ingredient.ingredientName IN :ingredientNames AND r.title like %:title% AND r.is_favourite = :isFavourite AND (r.privacyStatus = :privacyStatus OR :privacyStatus IS NULL) AND u.userId = :userId GROUP BY r HAVING COUNT(DISTINCT t) >= :tagCount AND COUNT(DISTINCT ri) >= :ingredientCount")
	Page<Recipe> findByTagsAndIngredientsAndUser(@Param("tagNames") ArrayList<String> tagNames, @Param("tagCount") long tagCount,
			@Param("ingredientNames") ArrayList<String> ingredientNames, @Param("ingredientCount") long ingredientCount,
			@Param("title") String title, @Param("isFavourite") boolean isFavourite,
			@Param("privacyStatus") PrivacyStatus privacyStatus, @Param("userId") Long userId, Pageable pageable);

}
