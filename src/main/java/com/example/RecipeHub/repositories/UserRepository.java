package com.example.RecipeHub.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.RecipeHub.entities.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	public Optional<User> findByEmail(String email);
	
	
//	public Page<User> filterUserAndPagination(String query, String sortBy, String direction, Pageable pageable);
//	
//	
//	public Page<User> filterUser(String query);
}
