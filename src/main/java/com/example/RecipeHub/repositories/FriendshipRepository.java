package com.example.RecipeHub.repositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.RecipeHub.entities.Friendship;

@Repository
public interface FriendshipRepository extends JpaRepository<Friendship, Long>{
//	ArrayList<Friendship>
}
