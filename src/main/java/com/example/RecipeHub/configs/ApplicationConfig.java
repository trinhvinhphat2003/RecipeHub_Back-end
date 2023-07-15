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
