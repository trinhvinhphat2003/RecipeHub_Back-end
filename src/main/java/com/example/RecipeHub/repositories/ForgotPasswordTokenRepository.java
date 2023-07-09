package com.example.RecipeHub.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.RecipeHub.entities.ForgotPasswordToken;
import com.example.RecipeHub.entities.User;

public interface ForgotPasswordTokenRepository extends JpaRepository<ForgotPasswordToken, Long>{
	Optional<ForgotPasswordToken> findByToken(String token);
	Optional<ForgotPasswordToken> findByUser(User user);
}
