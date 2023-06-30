package com.example.RecipeHub.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.RecipeHub.entities.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {

}
