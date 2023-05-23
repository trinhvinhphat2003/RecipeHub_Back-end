package com.example.RecipeHub.configs;

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

import com.example.RecipeHub.entities.FriendshipRequest;
import com.example.RecipeHub.entities.Ingredient;
import com.example.RecipeHub.entities.Recipe;
import com.example.RecipeHub.entities.Recipe_HAVE_Ingredient;
import com.example.RecipeHub.entities.Tag;
import com.example.RecipeHub.entities.User;
import com.example.RecipeHub.enums.Friendship_status;
import com.example.RecipeHub.enums.Gender;
import com.example.RecipeHub.enums.Role;
import com.example.RecipeHub.repositories.FriendshipRepository;
import com.example.RecipeHub.repositories.IngredientRepository;
import com.example.RecipeHub.repositories.RecipeRepository;
import com.example.RecipeHub.repositories.TagRepository;
import com.example.RecipeHub.repositories.UserRepository;
import com.example.RecipeHub.services.FriendService;
import com.example.RecipeHub.services.RecipeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Configuration
public class ApplicationConfig {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private FriendshipRepository friendshipRepository;

	@Autowired
	private FriendService friendService;

	@Autowired
	private RecipeService recipeService;

	@Autowired
	private IngredientRepository ingredientRepository;

	@Autowired
	private TagRepository tagRepository;
	
	@Autowired
	private RecipeRepository recipeRepository;
	
	@Bean
	public WebClient.Builder webClient() {
		return WebClient.builder();
	}

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
	}

	@Bean
	public CommandLineRunner getCommandLineRunner() {
		return new CommandLineRunner() {

			@Override
			public void run(String... args) throws Exception {
				// create user
				userRepository.save(new User("admin@gmail.com", getPasswordEncoder().encode("123456"), Role.ADMIN,
						"admin", Gender.MALE));
				userRepository.save(new User("user@gmail.com", getPasswordEncoder().encode("123456"), Role.USER, "user",
						Gender.MALE));
				userRepository.save(new User("user1@gmail.com", getPasswordEncoder().encode("123456"), Role.USER,
						"user1", Gender.FEMALE));
				userRepository.save(new User("user2@gmail.com", getPasswordEncoder().encode("123456"), Role.USER,
						"user2", Gender.FEMALE));

				// create friend request
				friendshipRepository.save(new FriendshipRequest(userRepository.findById(3l).get(),
						userRepository.findById(1l).get(), Friendship_status.WAITING));
				friendshipRepository.save(new FriendshipRequest(userRepository.findById(2l).get(),
						userRepository.findById(1l).get(), Friendship_status.WAITING));
				friendshipRepository.save(new FriendshipRequest(userRepository.findById(2l).get(),
						userRepository.findById(3l).get(), Friendship_status.ACCEPTED));
				friendshipRepository.save(new FriendshipRequest(userRepository.findById(2l).get(),
						userRepository.findById(4l).get(), Friendship_status.ACCEPTED));

				// sample add friend
				friendService.addFriend(2l, 1l);
				friendService.addFriend(2l, 3l);
				friendService.addFriend(2l, 4l);
				
				// create ingredient
				Ingredient ingredient1 = new Ingredient("Beaf");
				Ingredient ingredient2 = new Ingredient("Beaf 1");
				Ingredient ingredient3 = new Ingredient("Beaf 2");

				ingredientRepository.save(ingredient1);
				ingredientRepository.save(ingredient2);
				ingredientRepository.save(ingredient3);

				// create recipe
				User user = userRepository.findByEmail("admin@gmail.com").get();

				String recipeTag = "Dinner";
				String title = "admin Recipe 1";
				Integer preTime = 30;
				Integer cookTime = 60;
				Integer recipeYield = 4;
				Integer rating = 5;
				boolean isFavourite = true;
				String description = "This recipe is amazing!";
				String unit = "grams";
				String steps = "Step 1\nStep 2\nStep 3";
				String nutrition = "Protein: 10g\nCarbohydrates: 20g\nFat: 5g";

				Recipe recipe = new Recipe(user, title, preTime, cookTime, recipeYield, rating, isFavourite,
						description, unit, steps, nutrition);

				recipe.getIngredients().add(new Recipe_HAVE_Ingredient(recipe, ingredient1, "1 gram"));
				recipe.getIngredients().add(new Recipe_HAVE_Ingredient(recipe, ingredient2, "1 gram"));
				recipe.getIngredients().add(new Recipe_HAVE_Ingredient(recipe, ingredient3, "1 gram"));

				recipeService.save(recipe);

				recipeTag = "Dinner 1";
				title = "admin Recipe 2";
				preTime = 30;
				cookTime = 60;
				recipeYield = 4;
				rating = 5;
				isFavourite = true;
				description = "This recipe is amazing!";
				unit = "grams";
				steps = "Step 1\nStep 2\nStep 3";
				nutrition = "Protein: 10g\nCarbohydrates: 20g\nFat: 5g";

				recipe = new Recipe(user, title, preTime, cookTime, recipeYield, rating, isFavourite,
						description, unit, steps, nutrition);

				recipe.getIngredients().add(new Recipe_HAVE_Ingredient(recipe, ingredient2, "1 gram"));
				recipe.getIngredients().add(new Recipe_HAVE_Ingredient(recipe, ingredient3, "1 gram"));
				
				recipe.getTags().add(new Tag(null, recipeTag, null));
				//1
				recipeService.save(recipe);

				recipeTag = "Dinner 2";
				title = "admin Recipe 3";
				preTime = 30;
				cookTime = 60;
				recipeYield = 4;
				rating = 5;
				isFavourite = true;
				description = "This recipe is amazing!";
				unit = "grams";
				steps = "Step 1\nStep 2\nStep 3";
				nutrition = "Protein: 10g\nCarbohydrates: 20g\nFat: 5g";

				recipe = new Recipe(user, title, preTime, cookTime, recipeYield, rating, isFavourite,
						description, unit, steps, nutrition);

				recipe.getIngredients().add(new Recipe_HAVE_Ingredient(recipe, ingredient3, "1 gram"));

				recipe.getTags().add(new Tag(null, recipeTag, null));
				//2
				recipeService.save(recipe);

				user = userRepository.findByEmail("user@gmail.com").get();

				recipeTag = "Dinner 3";
				title = "user Recipe 1";
				preTime = 30;
				cookTime = 60;
				recipeYield = 4;
				rating = 5;
				isFavourite = true;
				description = "This recipe is amazing!";
				unit = "grams";
				steps = "Step 1\nStep 2\nStep 3";
				nutrition = "Protein: 10g\nCarbohydrates: 20g\nFat: 5g";

				recipe = new Recipe(user, title, preTime, cookTime, recipeYield, rating, isFavourite,
						description, unit, steps, nutrition);

				recipe.getIngredients().add(new Recipe_HAVE_Ingredient(recipe, ingredient1, "1 gram"));
				recipe.getIngredients().add(new Recipe_HAVE_Ingredient(recipe, ingredient2, "1 gram"));
				recipe.getIngredients().add(new Recipe_HAVE_Ingredient(recipe, ingredient3, "1 gram"));

				recipe.getTags().add(new Tag(null, recipeTag, null));
				//3
				recipeService.save(recipe);
				//3
				recipeTag = "Dinner 2";
				recipe = recipeRepository.findByTitle(title).get();
				Tag tag = tagRepository.findByTagName(recipeTag).get();
				recipe.getTags().add(tag);
				recipeService.save(recipe);

//				recipeTag = "Dinner 1";
//				title = "user Recipe 2";
//				preTime = 30;
//				cookTime = 60;
//				recipeYield = 4;
//				rating = 5;
//				isFavourite = true;
//				description = "This recipe is amazing!";
//				unit = "grams";
//				steps = "Step 1\nStep 2\nStep 3";
//				nutrition = "Protein: 10g\nCarbohydrates: 20g\nFat: 5g";
//
//				recipe = new Recipe(user, title, preTime, cookTime, recipeYield, rating, isFavourite,
//						description, unit, steps, nutrition);
//
//				recipe.getIngredients().add(new Recipe_HAVE_Ingredient(recipe, ingredient2, "1 gram"));
//				recipe.getIngredients().add(new Recipe_HAVE_Ingredient(recipe, ingredient3, "1 gram"));
//
//				Tag tag = tagRepository.findByTagName(recipeTag).get();
//				
//				recipe.getTags().add(tag);
//				
//				recipeService.save(recipe);
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
				return userRepository.findByEmail(email).orElseThrow(() -> {
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
