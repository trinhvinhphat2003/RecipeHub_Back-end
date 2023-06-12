package com.example.RecipeHub.repositories;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.RecipeHub.entities.Tag;

@Repository
public interface TagRepository extends JpaRepository<Tag, Long>{
	Optional<Tag> findByTagName(String tagName);
}
