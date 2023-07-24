package com.example.RecipeHub.services.impl;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.example.RecipeHub.services.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServiceImpl implements JwtService {
	private static final String SECRET_KEY = "38782F413F4428472B4B6150645367566B597033733676397924422645294840";

	public boolean isTokenValid(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
	}

	@Override
	public boolean isTokenExpired(String token) {
		Date expiredDate = extractExpirationDate(token);
		return expiredDate.before(new Date());
	}

	@Override
	public Date extractExpirationDate(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	@Override
	public <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
		final Claims claims = extractAllClaim(token);
		return claimResolver.apply(claims);
	}

	@Override
	public String generateToken(UserDetails userDetails) {
		return generateToken(userDetails, new HashMap<>());
	}

	@Override
	public String generateToken(UserDetails userDetails, Map<String, Object> extractClaim) {
		return Jwts.builder().setClaims(extractClaim).setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 10))
				.signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();

	}

	@Override
	public String generateForgotPasswordToken(Map<String, Object> extractClaim) {
		return Jwts.builder().setClaims(extractClaim).setSubject("forgotpassword token")
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 2))
				.signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();

	}

	@Override
	public String generateTokenForRegisterVerification(String userJson) {
		return Jwts.builder().setClaims(new HashMap<>()).setSubject(userJson)
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 2))
				.signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
	}

	@Override
	public String extractUsername(String jwtToken) {
		String email = extractClaim(jwtToken, Claims::getSubject);
		return email;
	}

	@Override
	public String extractSubject(String jwtToken) {
		String email = extractClaim(jwtToken, Claims::getSubject);
		return email;
	}

	private Claims extractAllClaim(String token) {
		return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token).getBody();
	}

	@Override
	public Key getSigningKey() {
		byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);
		return Keys.hmacShaKeyFor(keyBytes);
	}

}
