package com.example.RecipeHub.controllers.client.apis;

import java.util.ArrayList;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.RecipeHub.dtos.FIlterDTO;
import com.example.RecipeHub.dtos.IngredientDTO;
import com.example.RecipeHub.dtos.RecipeDTO;
import com.example.RecipeHub.dtos.TagDTO;
import com.example.RecipeHub.entities.Ingredient;
import com.example.RecipeHub.entities.Recipe;
import com.example.RecipeHub.entities.Tag;
import com.example.RecipeHub.entities.User;
import com.example.RecipeHub.enums.PrivacyStatus;
import com.example.RecipeHub.errorHandlers.UnauthorizedExeption;
import com.example.RecipeHub.eventListeners.events.RegistrationCompletionEvent;
import com.example.RecipeHub.eventListeners.events.ShareRecipeEvent;
import com.example.RecipeHub.mappers.RecipeMapper;
import com.example.RecipeHub.services.RecipeService;
import com.example.RecipeHub.services.UserService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/v1/")
public class RecipeController {

	private final RecipeService recipeService;
	private final UserService userService;
	private final ApplicationEventPublisher eventPublisher;

	public RecipeController(RecipeService recipeService, UserService userService, ApplicationEventPublisher eventPublisher) {
		super();
		this.recipeService = recipeService;
		this.userService = userService;
		this.eventPublisher = eventPublisher;
	}

	@PostMapping("user/recipe")
	public ResponseEntity<String> addNewRecipe(@RequestParam(required = false) MultipartFile[] files,
			@RequestPart("data") RecipeDTO dto, @AuthenticationPrincipal User user,
			HttpServletRequest httpServletRequest) {
		dto.setRecipe_id(null);
		dto.setUserId(user.getUserId());
		recipeService.addNewRecipe(dto, files, user.getUserId(), httpServletRequest);
		return ResponseEntity.ok("ok");
	}

	@PutMapping("user/recipe/{recipe_id}")
	public ResponseEntity<String> editRecipe(@RequestParam(required = false) MultipartFile[] files,
			@RequestPart("data") RecipeDTO dto, @AuthenticationPrincipal User user, @PathVariable("recipe_id") Long recipeId, HttpServletRequest httpServletRequest) {
		recipeService.editRecipe(dto, recipeId, user.getUserId(), files, httpServletRequest);
		return ResponseEntity.ok("Recipe have been editted");
	}

	@GetMapping("user/recipes")
	public ResponseEntity<ArrayList<RecipeDTO>> getAllRecipesForUser(@AuthenticationPrincipal User user) {
		ArrayList<RecipeDTO> recipeDtos = recipeService.getAllRecipesByUser(user);
		return new ResponseEntity<>(recipeDtos, HttpStatus.OK);
	}

	@GetMapping("user/recipe/{recipe_id}")
	public ResponseEntity<RecipeDTO> getOneRecipeByUser(@AuthenticationPrincipal User user,
			@PathVariable("recipe_id") Long recipeId) {
		RecipeDTO recipeDtos = recipeService.getRecipeDTOById(recipeId);
		return new ResponseEntity<>(recipeDtos, HttpStatus.OK);
	}
	
	@GetMapping("global/recipe/{recipe_id}")
	public ResponseEntity<RecipeDTO> getOneRecipeGlobal(@AuthenticationPrincipal User user,
			@PathVariable("recipe_id") Long recipeId) {
		RecipeDTO recipeDtos = recipeService.getRecipeDTOById(recipeId);
		return new ResponseEntity<>(recipeDtos, HttpStatus.OK);
	}

	@DeleteMapping("user/recipe/{recipe_id}")
	public ResponseEntity<String> deleteOneRecipeByUser(@AuthenticationPrincipal User user,
			@PathVariable("recipe_id") Long recipeId) {
		recipeService.deleteOneUserRecipeById(recipeId, user.getUserId());
		return new ResponseEntity<>("deleted recipe have id " + recipeId + " successfully", HttpStatus.OK);
	}

	@GetMapping("global/recipes")
	public ResponseEntity<ArrayList<RecipeDTO>> getAllRecipes(
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "2") int size,
			@RequestParam(value = "verified", required = false) Boolean verified) {

		ArrayList<RecipeDTO> recipeDtos = recipeService.getRecipesWithFilter(
				new FIlterDTO(null, null, null, null, null, null, PrivacyStatus.PUBLIC.name(), null), page, size, null, verified);
		return new ResponseEntity<>(recipeDtos, HttpStatus.OK);
	}

	@PostMapping("global/recipes/filter")
	public ResponseEntity<ArrayList<RecipeDTO>> getAllRecipesWithFilter(@RequestBody FIlterDTO fIlterDTO,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "20") int size) {
		fIlterDTO.setPrivacyStatus(PrivacyStatus.PUBLIC.name());
		ArrayList<RecipeDTO> recipeDtos = recipeService.getRecipesWithFilter(fIlterDTO, page, size, null, true);
		return new ResponseEntity<>(recipeDtos, HttpStatus.OK);
	}
	
	@PostMapping("global/recipes/filter/total-item")
	public ResponseEntity<Long> getTotalItemOfAllRecipesWithFilter(@RequestBody FIlterDTO fIlterDTO,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "20") int size) {
		fIlterDTO.setPrivacyStatus(PrivacyStatus.PUBLIC.name());
		return ResponseEntity.ok(recipeService.getTotalItemOfRecipesWithFilter(fIlterDTO, page, size, null, true));
	}

//	@PostMapping("global/recipes/filter/test")
//	public ResponseEntity<ArrayList<RecipeDTO>> getAllRecipesWithFilterTest(@RequestBody FIlterDTO fIlterDTO,
//			@RequestParam(value = "page", defaultValue = "0") int page,
//			@RequestParam(value = "size", defaultValue = "2") int size) {
//		ArrayList<RecipeDTO> recipeDtos = recipeService.getRecipesWithFilter(fIlterDTO, page, size, 1L);
//		return new ResponseEntity<>(recipeDtos, HttpStatus.OK);
//	}

	@PostMapping("user/recipes/filter")
	public ResponseEntity<ArrayList<RecipeDTO>> getAllRecipesByUserWithFilter(@AuthenticationPrincipal User user,
			@RequestBody FIlterDTO fIlterDTO, @RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "20") int size,
			@RequestParam(value = "verified", required = false) Boolean verified) {
		ArrayList<RecipeDTO> recipeDtos = recipeService.getRecipesWithFilter(fIlterDTO, page, size, user.getUserId(), fIlterDTO.getVerified());
		return new ResponseEntity<>(recipeDtos, HttpStatus.OK);
	}
	
	@PostMapping("user/recipes/filter/total-item")
	public ResponseEntity<Long> getTotalItemOfAllRecipesByUserWithFilter(@AuthenticationPrincipal User user,
			@RequestBody FIlterDTO fIlterDTO, @RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "20") int size) {
		return ResponseEntity.ok(recipeService.getTotalItemOfRecipesWithFilter(fIlterDTO, page, size, user.getUserId(), fIlterDTO.getVerified()));
	}

	@PostMapping("global/recipes/filter/{user_id}")
	public ResponseEntity<ArrayList<RecipeDTO>> getAllRecipesWithFilter(@RequestBody FIlterDTO fIlterDTO,
			@PathVariable("user_id") Long user_id, @RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "20") int size,
			@RequestParam(value = "verified", required = false) Boolean verified) {
		fIlterDTO.setPrivacyStatus(PrivacyStatus.PUBLIC.name());
		ArrayList<RecipeDTO> recipeDtos = recipeService.getRecipesWithFilter(fIlterDTO, page, size, user_id, verified);
		return new ResponseEntity<>(recipeDtos, HttpStatus.OK);
	}
	
	@PostMapping("global/recipes/filter/{user_id}/total-item")
	public ResponseEntity<Long> getTotalItemOfAllRecipesWithFilter(@RequestBody FIlterDTO fIlterDTO,
			@PathVariable("user_id") Long user_id, @RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "20") int size,
			@RequestParam(value = "verified", required = false) Boolean verified) {
		fIlterDTO.setPrivacyStatus(PrivacyStatus.PUBLIC.name());
		return ResponseEntity.ok(recipeService.getTotalItemOfRecipesWithFilter(fIlterDTO, page, size, user_id, verified));
	}
	
	@PostMapping("user/copy-recipe/{recipeId}")
	public ResponseEntity<Long> copyOtherRecipe(@AuthenticationPrincipal User user, @PathVariable("recipeId") Long recipeId, HttpServletRequest httpServletRequest) {
		User userEntity = userService.getUserById(user.getUserId());
		Long newId = recipeService.copyRecipe(userEntity, recipeId, httpServletRequest);
		return ResponseEntity.ok(newId);
	}
	
	@PostMapping("user/share-recipe/{recipeId}")
	public ResponseEntity<String> shareRecipe(@AuthenticationPrincipal User user, @PathVariable("recipeId") Long recipeid, @RequestBody String[] emails) throws Exception {
		eventPublisher.publishEvent(new ShareRecipeEvent(emails, user.getFullName(), recipeid));
		return ResponseEntity.ok("share recipe to friends successfully");
	}
}
