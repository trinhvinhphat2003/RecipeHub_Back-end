package com.example.RecipeHub.configs;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.reactive.function.client.WebClient;

import com.example.RecipeHub.entities.Friendship;
import com.example.RecipeHub.entities.User;
import com.example.RecipeHub.enums.Friendship_status;
import com.example.RecipeHub.enums.Role;
import com.example.RecipeHub.repositories.FriendshipRepository;
import com.example.RecipeHub.repositories.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


@Configuration
public class ApplicationConfig {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private FriendshipRepository friendshipRepository;
	
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
				userRepository.save(new User("admin@gmail.com", getPasswordEncoder().encode("123456"), Role.ADMIN));
				userRepository.save(new User("user@gmail.com", getPasswordEncoder().encode("123456"), Role.USER));
				userRepository.save(new User("user1@gmail.com", getPasswordEncoder().encode("123456"), Role.USER));
				userRepository.save(new User("user2@gmail.com", getPasswordEncoder().encode("123456"), Role.USER));
				
				friendshipRepository.save(new Friendship(userRepository.findById(2l).get(), userRepository.findById(1l).get(), Friendship_status.ACCEPTED));
				friendshipRepository.save(new Friendship(userRepository.findById(2l).get(), userRepository.findById(3l).get(), Friendship_status.ACCEPTED));
				friendshipRepository.save(new Friendship(userRepository.findById(2l).get(), userRepository.findById(4l).get(), Friendship_status.ACCEPTED));
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
