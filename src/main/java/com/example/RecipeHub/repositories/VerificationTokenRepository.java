package com.example.RecipeHub.repositories;

import com.example.RecipeHub.entities.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, String> {
    Optional<VerificationToken> findVerificationTokenByToken(String verificationToken);
}