package com.example.RecipeHub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class RecipeHubApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecipeHubApplication.class, args);
	}

}
