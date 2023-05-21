package com.example.RecipeHub.repositories;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.RecipeHub.entities.FriendshipRequest;

@Repository
public interface FriendshipRepository extends JpaRepository<FriendshipRequest, Long>{
//	ArrayList<Friendship>
}
