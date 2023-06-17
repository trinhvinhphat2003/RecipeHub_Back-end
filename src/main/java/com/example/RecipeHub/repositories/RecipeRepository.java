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
	public Page<Recipe> findAllGlobalRecipeByPrivacyStatus(@Param("title") String title,
			@Param("isFavourite") boolean isFavourite, @Param("privacyStatus") PrivacyStatus privacyStatus,
			Pageable pageable);

	Optional<Recipe> findByTitle(String title);

	@Query("SELECT r FROM Recipe r where r.title like %:title%")
	Page<Recipe> findByTitle(@Param("title") String title, Pageable pageable);

}
