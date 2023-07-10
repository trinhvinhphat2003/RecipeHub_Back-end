package com.example.RecipeHub.configs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

import com.example.RecipeHub.entities.Image;
import com.example.RecipeHub.entities.Ingredient;
import com.example.RecipeHub.entities.Meal_planner;
import com.example.RecipeHub.entities.Recipe;
import com.example.RecipeHub.entities.SupportTicket;
import com.example.RecipeHub.entities.Tag;
import com.example.RecipeHub.entities.User;
import com.example.RecipeHub.enums.Gender;
import com.example.RecipeHub.enums.LoginType;
import com.example.RecipeHub.enums.MealType;
import com.example.RecipeHub.enums.PrivacyStatus;
import com.example.RecipeHub.enums.Role;
import com.example.RecipeHub.enums.SupportTicketStatus;
import com.example.RecipeHub.repositories.FriendshipRepository;
import com.example.RecipeHub.repositories.IngredientRepository;
import com.example.RecipeHub.repositories.MealPlannerRepository;
import com.example.RecipeHub.repositories.RecipeCustomRepository;
import com.example.RecipeHub.repositories.RecipeRepository;
import com.example.RecipeHub.repositories.SupportTicketRepository;
import com.example.RecipeHub.repositories.TagRepository;
import com.example.RecipeHub.repositories.UserRepository;
import com.example.RecipeHub.services.FriendService;
import com.example.RecipeHub.services.RecipeService;
import com.example.RecipeHub.utils.DateTimeUtil;
import com.example.RecipeHub.utils.FileUtil;
import com.example.RecipeHub.utils.SystemUtil;
import com.example.RecipeHub.utils.TagDefaultConstant;
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
	
	@Autowired
	private MealPlannerRepository mealPlannerRepository;
	
	@Autowired
	private RecipeCustomRepository recipeCustomRepository;
	
	@Autowired
	private SupportTicketRepository supportTicketRepository;

	@Bean
	public WebClient.Builder webClient() {
		return WebClient.builder();
	}

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper().disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
	}

	@Value("${avatar.image.api.baseurl}")
	private String avartarBaseUrl;
	
	@Bean
	public CommandLineRunner getCommandLineRunner() {
		return new CommandLineRunner() {

			@Override
			public void run(String... args) throws Exception {
				
				//init
				String image1 = "https://recipehub.herokuapp.com/api/v1/global/image/recipe/192cf0a4-d69f-4add-99d5-c1bb3778b5f2.jpg";
				
				//create support ticket
				supportTicketRepository.save(new SupportTicket(null, "trinhvinhphat123@gmail.com", "hello", SupportTicketStatus.PENDING));
				supportTicketRepository.save(new SupportTicket(null, "hello123@gmail.com", "hello", SupportTicketStatus.PENDING));
				supportTicketRepository.save(new SupportTicket(null, "eololo123@gmail.com", "hihihihihi", SupportTicketStatus.PENDING));
				
				// create user
				userRepository.save(new User("admin@gmail.com", getPasswordEncoder().encode("123456"), Role.ADMIN,
						"admin", Gender.MALE, true, DateTimeUtil.milisecondToDate(System.currentTimeMillis()), avartarBaseUrl + "default.jpg", LoginType.BASIC, false));
				userRepository.save(new User("user@gmail.com", getPasswordEncoder().encode("123456"), Role.USER, "user",
						Gender.MALE, true, DateTimeUtil.milisecondToDate(System.currentTimeMillis()), avartarBaseUrl + "default.jpg", LoginType.BASIC, false));
				userRepository.save(new User("user1@gmail.com", getPasswordEncoder().encode("123456"), Role.USER,
						"user1", Gender.FEMALE, true, DateTimeUtil.milisecondToDate(System.currentTimeMillis()), avartarBaseUrl + "default.jpg", LoginType.BASIC, false));
				userRepository.save(new User("user2@gmail.com", getPasswordEncoder().encode("123456"), Role.USER,
						"user2", Gender.FEMALE, true, DateTimeUtil.milisecondToDate(System.currentTimeMillis()), avartarBaseUrl + "default.jpg", LoginType.BASIC, false));
				userRepository.save(new User("danevil2003@gmail.com", getPasswordEncoder().encode("123456"), Role.ADMIN,
						"Phat", Gender.FEMALE, true, DateTimeUtil.milisecondToDate(System.currentTimeMillis()), avartarBaseUrl + "default.jpg", LoginType.BASIC, false));

				//create default tags
				for(String tag : TagDefaultConstant.TAGS_DEFAULT) {
					tagRepository.save(new Tag(tag));
				}
				
				// create friend request
//				friendshipRepository.save(new FriendshipRequest(userRepository.findById(3l).get(),
//						userRepository.findById(1l).get(), Friendship_status.WAITING));
//				friendshipRepository.save(new FriendshipRequest(userRepository.findById(2l).get(),
//						userRepository.findById(1l).get(), Friendship_status.WAITING));
//				friendshipRepository.save(new FriendshipRequest(userRepository.findById(2l).get(),
//						userRepository.findById(3l).get(), Friendship_status.ACCEPTED));
//				friendshipRepository.save(new FriendshipRequest(userRepository.findById(2l).get(),
//						userRepository.findById(4l).get(), Friendship_status.ACCEPTED));

				// sample add friend
//				friendService.addFriend(2l, 1l);
//				friendService.addFriend(2l, 3l);
//				friendService.addFriend(2l, 4l);

				// create recipe
				User admin = userRepository.findByEmail("admin@gmail.com").get();
				User user = userRepository.findByEmail("user@gmail.com").get();
				
				Tag lunch = tagRepository.findByTagName("lunch").get();
				Tag dinner = tagRepository.findByTagName("dinner").get();
				
				//1
				String recipeTag = "dinner";
				String title = "admin Recipe 1";
				Integer preTime = 30;
				Integer cookTime = 60;
				Integer recipeYield = 4;
				Integer rating = 5;
				boolean isFavourite = true;
				String description = "This recipe 0 is amazing!";
				String unit = "grams";
				String steps = "Step 1\nStep 2\nStep 3";
				String nutrition = "Protein: 10g\nCarbohydrates: 20g\nFat: 5g";
				PrivacyStatus privacyStatus = PrivacyStatus.PUBLIC;

				Recipe recipe = new Recipe(admin, title, preTime, cookTime, recipeYield, rating, isFavourite,
						description, unit, steps, nutrition, privacyStatus);

				recipe.getIngredients().add(new Ingredient(null, recipe, "onion", "100 gram"));
				recipe.getIngredients().add(new Ingredient(null, recipe, "carrot", "1 gram"));
				
				recipe.getImages().add(new Image(null, image1, recipe));
				recipe.getImages().add(new Image(null, image1, recipe));

				recipe = recipeRepository.save(recipe);
				
				recipe.getTags().add(dinner);
				
				recipe = recipeRepository.save(recipe);
				
				mealPlannerRepository.save(new Meal_planner(null, recipe, admin, MealType.DINNER, DateTimeUtil.milisecondToDate(System.currentTimeMillis() - (1000*60*60*24*2))));
				mealPlannerRepository.save(new Meal_planner(null, recipe, admin, MealType.DINNER, DateTimeUtil.milisecondToDate(System.currentTimeMillis())));
				
				//2
				recipeTag = "dinner";
				title = "admin Recipe 2";
				preTime = 30;
				cookTime = 60;
				recipeYield = 4;
				rating = 5;
				isFavourite = true;
				description = "This recipe 1 is amazing!";
				unit = "grams";
				steps = "Step 1\nStep 2\nStep 3";
				nutrition = "Protein: 10g\nCarbohydrates: 20g\nFat: 5g";
				privacyStatus = PrivacyStatus.PRIVATE;

				recipe = new Recipe(admin, title, preTime, cookTime, recipeYield, rating, isFavourite, description, unit,
						steps, nutrition, privacyStatus);

				recipe.getIngredients().add(new Ingredient(null, recipe, "beaf", "1 gram"));
				recipe.getIngredients().add(new Ingredient(null, recipe, "carrot", "1 gram"));
				
				recipe.getImages().add(new Image(null, image1, recipe));

				recipe = recipeRepository.save(recipe);
				
				recipe.getTags().add(dinner);
				recipe.getTags().add(lunch);
				
				recipeService.save(recipe);
				
				//3
				recipeTag = "noon";
				title = "user Recipe 1";
				preTime = 30;
				cookTime = 60;
				recipeYield = 4;
				rating = 5;
				isFavourite = true;
				description = "This recipe 2 is amazing!";
				unit = "grams";
				steps = "Step 1\nStep 2\nStep 3";
				nutrition = "Protein: 10g\nCarbohydrates: 20g\nFat: 5g";
				privacyStatus = PrivacyStatus.PUBLIC;

				recipe = new Recipe(user, title, preTime, cookTime, recipeYield, rating, isFavourite, description, unit,
						steps, nutrition, privacyStatus);

				recipe.getIngredients().add(new Ingredient(null, recipe, "carrot", "1 gram"));
				recipe.getIngredients().add(new Ingredient(null, recipe, "beaf", "1 gram"));
				
				recipe.getImages().add(new Image(null, image1, recipe));

				recipe = recipeRepository.save(recipe);
				
				recipe.getTags().add(lunch);
				recipe.getTags().add(new Tag("hello"));
				
				recipeService.save(recipe);
				//4
				recipeTag = "noon";
				title = "user Recipe 2";
				preTime = 30;
				cookTime = 60;
				recipeYield = 4;
				rating = 4;
				isFavourite = true;
				description = "This recipe 3 is amazing!";
				unit = "grams";
				steps = "Step 1\nStep 2\nStep 3";
				nutrition = "Protein: 10g\nCarbohydrates: 20g\nFat: 5g";
				privacyStatus = PrivacyStatus.PRIVATE;

				recipe = new Recipe(user, title, preTime, cookTime, recipeYield, rating, isFavourite, description, unit,
						steps, nutrition, privacyStatus);

				recipe.getIngredients().add(new Ingredient(null, recipe, "carrot", "1 gram"));
				recipe.getIngredients().add(new Ingredient(null, recipe, "beaf", "1 gram"));
				recipe.getIngredients().add(new Ingredient(null, recipe, "onion", "1 gram"));

				recipe = recipeRepository.save(recipe);
				
				recipe.getTags().add(lunch);
				recipe.getImages().add(new Image(null, image1, recipe));
				
				recipe = recipeRepository.save(recipe);
				recipeTag = "breakfast";
				recipe.getTags().add(tagRepository.findByTagName(recipeTag).get());
				recipeService.save(recipe);
				
				//5
				recipeTag = "dinner";
				title = "admin Recipe 3";
				preTime = 30;
				cookTime = 60;
				recipeYield = 4;
				rating = 5;
				isFavourite = true;
				description = "This recipe 0 is amazing!";
				unit = "grams";
				steps = "Step 1\nStep 2\nStep 3";
				nutrition = "Protein: 10g\nCarbohydrates: 20g\nFat: 5g";
				privacyStatus = PrivacyStatus.PUBLIC;

				recipe = new Recipe(admin, title, preTime, cookTime, recipeYield, rating, isFavourite,
						description, unit, steps, nutrition, privacyStatus);

				recipe.getIngredients().add(new Ingredient(null, recipe, "onion", "100 gram"));
				recipe.getIngredients().add(new Ingredient(null, recipe, "carrot", "1 gram"));
				
				recipe.getImages().add(new Image(null, image1, recipe));

				recipe = recipeRepository.save(recipe);
				
				recipe.getTags().add(dinner);
				
				recipe = recipeRepository.save(recipe);
				
				//6
				recipeTag = "dinner";
				title = "admin Recipe 4";
				preTime = 30;
				cookTime = 60;
				recipeYield = 4;
				rating = 5;
				isFavourite = true;
				description = "This recipe 0 is amazing!";
				unit = "grams";
				steps = "Step 1\nStep 2\nStep 3";
				nutrition = "Protein: 10g\nCarbohydrates: 20g\nFat: 5g";
				privacyStatus = PrivacyStatus.PUBLIC;

				recipe = new Recipe(admin, title, preTime, cookTime, recipeYield, rating, isFavourite,
						description, unit, steps, nutrition, privacyStatus);

				recipe.getIngredients().add(new Ingredient(null, recipe, "onion", "100 gram"));
				recipe.getIngredients().add(new Ingredient(null, recipe, "carrot", "1 gram"));
				
				recipe.getImages().add(new Image(null, image1, recipe));
				recipe.getImages().add(new Image(null, image1, recipe));
				recipe.getImages().add(new Image(null, image1, recipe));
				recipe.getImages().add(new Image(null, image1, recipe));
				recipe.getImages().add(new Image(null, image1, recipe));
				recipe.getImages().add(new Image(null, image1, recipe));

				recipe = recipeRepository.save(recipe);
				
				recipe.getTags().add(dinner);
				
				recipe = recipeRepository.save(recipe);
				
				//7
				recipeTag = "dinner";
				title = "admin Recipe 5";
				preTime = 30;
				cookTime = 60;
				recipeYield = 4;
				rating = 5;
				isFavourite = true;
				description = "This recipe 0 is amazing!";
				unit = "grams";
				steps = "Step 1\nStep 2\nStep 3";
				nutrition = "Protein: 10g\nCarbohydrates: 20g\nFat: 5g";
				privacyStatus = PrivacyStatus.PUBLIC;

				recipe = new Recipe(admin, title, preTime, cookTime, recipeYield, rating, isFavourite,
						description, unit, steps, nutrition, privacyStatus);

				recipe.getIngredients().add(new Ingredient(null, recipe, "onion", "100 gram"));
				recipe.getIngredients().add(new Ingredient(null, recipe, "carrot", "1 gram"));
				
				recipe.getImages().add(new Image(null, image1, recipe));
				recipe.getImages().add(new Image(null, image1, recipe));
				recipe.getImages().add(new Image(null, image1, recipe));
				recipe.getImages().add(new Image(null, image1, recipe));
				recipe.getImages().add(new Image(null, image1, recipe));
				recipe.getImages().add(new Image(null, image1, recipe));

				recipe = recipeRepository.save(recipe);
				
				recipe.getTags().add(dinner);
				
				recipe = recipeRepository.save(recipe);
				
				//8
				recipeTag = "noon";
				title = "user Recipe 3";
				preTime = 30;
				cookTime = 60;
				recipeYield = 4;
				rating = 4;
				isFavourite = true;
				description = "This recipe 3 is amazing!";
				unit = "grams";
				steps = "Step 1\nStep 2\nStep 3";
				nutrition = "Protein: 10g\nCarbohydrates: 20g\nFat: 5g";
				privacyStatus = PrivacyStatus.PRIVATE;

				recipe = new Recipe(user, title, preTime, cookTime, recipeYield, rating, isFavourite, description, unit,
						steps, nutrition, privacyStatus);

				recipe.getIngredients().add(new Ingredient(null, recipe, "carrot", "1 gram"));
				recipe.getIngredients().add(new Ingredient(null, recipe, "beaf", "1 gram"));
				recipe.getIngredients().add(new Ingredient(null, recipe, "onion", "1 gram"));

				recipe = recipeRepository.save(recipe);
				
				recipe.getTags().add(lunch);
				recipe.getImages().add(new Image(null, image1, recipe));
				recipe.getImages().add(new Image(null, image1, recipe));
				recipe.getImages().add(new Image(null, image1, recipe));
				
				recipe = recipeRepository.save(recipe);
				
				//9
				recipeTag = "noon";
				title = "user Recipe 4";
				preTime = 30;
				cookTime = 60;
				recipeYield = 4;
				rating = 4;
				isFavourite = true;
				description = "This recipe 3 is amazing!";
				unit = "grams";
				steps = "Step 1\nStep 2\nStep 3";
				nutrition = "Protein: 10g\nCarbohydrates: 20g\nFat: 5g";
				privacyStatus = PrivacyStatus.PRIVATE;

				recipe = new Recipe(user, title, preTime, cookTime, recipeYield, rating, isFavourite, description, unit,
						steps, nutrition, privacyStatus);

				recipe.getIngredients().add(new Ingredient(null, recipe, "carrot", "1 gram"));
				recipe.getIngredients().add(new Ingredient(null, recipe, "beaf", "1 gram"));
				recipe.getIngredients().add(new Ingredient(null, recipe, "onion", "1 gram"));

				recipe = recipeRepository.save(recipe);
				
				recipe.getTags().add(lunch);
				recipe.getImages().add(new Image(null, image1, recipe));
				recipe.getImages().add(new Image(null, image1, recipe));
				recipe.getImages().add(new Image(null, image1, recipe));
				
				recipe = recipeRepository.save(recipe);
				
				//10
				recipeTag = "noon";
				title = "user Recipe 5";
				preTime = 30;
				cookTime = 60;
				recipeYield = 4;
				rating = 4;
				isFavourite = true;
				description = "This recipe 3 is amazing!";
				unit = "grams";
				steps = "Step 1\nStep 2\nStep 3";
				nutrition = "Protein: 10g\nCarbohydrates: 20g\nFat: 5g";
				privacyStatus = PrivacyStatus.PRIVATE;

				recipe = new Recipe(user, title, preTime, cookTime, recipeYield, rating, isFavourite, description, unit,
						steps, nutrition, privacyStatus);

				recipe.getIngredients().add(new Ingredient(null, recipe, "carrot", "1 gram"));
				recipe.getIngredients().add(new Ingredient(null, recipe, "beaf", "1 gram"));
				recipe.getIngredients().add(new Ingredient(null, recipe, "onion", "1 gram"));

				recipe = recipeRepository.save(recipe);
				
				recipe.getTags().add(lunch);
				recipe.getImages().add(new Image(null, image1, recipe));
				recipe.getImages().add(new Image(null, image1, recipe));
				recipe.getImages().add(new Image(null, image1, recipe));
				
				recipe = recipeRepository.save(recipe);
				System.out.println(DateTimeUtil.milisecondToDateString(System.currentTimeMillis()));
				System.out.println(System.currentTimeMillis()-(1000*60*60*24*2));
				System.out.println(System.currentTimeMillis() + (1000*60*60*24*2));

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
