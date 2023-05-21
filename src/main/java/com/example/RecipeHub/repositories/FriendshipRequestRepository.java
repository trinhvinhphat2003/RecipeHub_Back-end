package com.example.RecipeHub.repositories;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.RecipeHub.entities.FriendshipRequest;
import com.example.RecipeHub.entities.User;
import com.example.RecipeHub.enums.Friendship_status;

@Repository
public interface FriendshipRequestRepository extends JpaRepository<FriendshipRequest, Long>{
	ArrayList<FriendshipRequest> findAllByReceiverAndStatus(User receiver, Friendship_status status);
}
