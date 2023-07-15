package com.example.RecipeHub.services;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

public interface JwtService {
	public boolean isTokenValid(String token, UserDetails userDetails);

	public boolean isTokenExpired(String token);

	public Date extractExpirationDate(String token);

	public <T> T extractClaim(String token, Function<Claims, T> claimResolver);

	public String generateToken(UserDetails userDetails);

	public String generateToken(UserDetails userDetails, Map<String, Object> extractClaim);

	public String generateForgotPasswordToken(Map<String, Object> extractClaim);

	public String generateTokenForRegisterVerification(String userJson);

	public String extractUsername(String jwtToken);

	public String extractSubject(String jwtToken);

	public Key getSigningKey();

}
