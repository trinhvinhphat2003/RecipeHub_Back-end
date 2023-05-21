package com.example.RecipeHub.configs;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.RecipeHub.entities.FriendshipRequest;
import com.example.RecipeHub.entities.User;
import com.example.RecipeHub.enums.Friendship_status;
import com.example.RecipeHub.enums.Gender;
import com.example.RecipeHub.enums.Role;
import com.example.RecipeHub.repositories.FriendshipRepository;
import com.example.RecipeHub.repositories.UserRepository;
import com.example.RecipeHub.services.FriendService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import jakarta.transaction.Transactional;


@Configuration
public class ApplicationConfig {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private FriendshipRepository friendshipRepository;
	
	@Autowired
	private FriendService friendService;
	
	@Bean
    public WebClient.Builder webClient() {
        return WebClient.builder();
    }
	
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper()
                .disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    }
	
	@Bean
	public CommandLineRunner getCommandLineRunner() {
		return new CommandLineRunner() {
			
			@Override
			public void run(String... args) throws Exception {
				userRepository.save(new User("admin@gmail.com", getPasswordEncoder().encode("123456"), Role.ADMIN, "admin", Gender.MALE));
				userRepository.save(new User("user@gmail.com", getPasswordEncoder().encode("123456"), Role.USER, "user", Gender.MALE));
				userRepository.save(new User("user1@gmail.com", getPasswordEncoder().encode("123456"), Role.USER, "user1", Gender.FEMALE));
				userRepository.save(new User("user2@gmail.com", getPasswordEncoder().encode("123456"), Role.USER, "user2", Gender.FEMALE));
				
				
				
//				friendshipRepository.save(new FriendshipRequest(userRepository.findById(2l).get(), userRepository.findById(1l).get(), Friendship_status.ACCEPTED));
//				friendshipRepository.save(new FriendshipRequest(userRepository.findById(2l).get(), userRepository.findById(3l).get(), Friendship_status.ACCEPTED));
//				friendshipRepository.save(new FriendshipRequest(userRepository.findById(2l).get(), userRepository.findById(4l).get(), Friendship_status.ACCEPTED));
//				
				friendService.addFriend(2l, 1l);
				friendService.addFriend(2l, 3l);
				friendService.addFriend(2l, 4l);
			}
		};
	}
	
	@Bean
	public AuthenticationProvider getAuthenticationProvider() {
		DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(getUserDetailsService());
		daoAuthenticationProvider.setPasswordEncoder(getPasswordEncoder());
		return daoAuthenticationProvider;
	}
	
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public UserDetailsService getUserDetailsService() {
		return new UserDetailsService() {
			
			@Override
			public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
				// TODO Auto-generated method stub
				try {
					Optional<User> user = userRepository.findByEmail(email);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				return userRepository.findByEmail(email)
						.orElseThrow(() -> {
							return new UsernameNotFoundException("Account not found");
						});
			}
		};
	}
	
	@Bean
	public AuthenticationManager getAuthenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
	
}
