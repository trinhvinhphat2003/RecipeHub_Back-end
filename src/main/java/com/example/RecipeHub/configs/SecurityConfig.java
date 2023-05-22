package com.example.RecipeHub.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.RecipeHub.enums.Role;
import com.example.RecipeHub.filters.JwtFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	private final JwtFilter jwtFilter;

	private final AuthenticationProvider authenticationProvider;

	public SecurityConfig(JwtFilter jwtFilter, AuthenticationProvider authenticationProvider) {
		this.jwtFilter = jwtFilter;
		this.authenticationProvider = authenticationProvider;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.formLogin().disable();

		http.authorizeHttpRequests()
//			.requestMatchers("/api/user").hasAnyAuthority(Role.USER.name(), Role.ADMIN.name())
				.requestMatchers("/admin/api/v1/**").hasAuthority(Role.ADMIN.name()).requestMatchers("/api/v1/auth/**")
				.permitAll()
//			.requestMatchers("/api/v1/**").permitAll()
				.anyRequest().authenticated().and().sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
				.authenticationProvider(authenticationProvider)
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
//			.exceptionHandling()
//        	.accessDeniedHandler(customAccessDeniedHandler)
//        	.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
		return http.build();
	}
}
