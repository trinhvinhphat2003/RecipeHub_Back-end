package com.example.RecipeHub.filters;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.RecipeHub.services.JwtService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private JwtService jwtService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String JwtHeader = request.getHeader("Authentication");
		if (JwtHeader == null || !JwtHeader.startsWith("Bearer")) {
			filterChain.doFilter(request, response);
			return;
		}
		
		String jwtToken = JwtHeader.substring(7);
		String userEmail = jwtService.extractUsername(jwtToken);

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (userEmail != null && auth == null) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);
			final boolean isTokenValid = jwtService.isTokenValid(jwtToken, userDetails);
			if (isTokenValid) {
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				usernamePasswordAuthenticationToken
						.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
		}
		filterChain.doFilter(request, response);

	}

}
