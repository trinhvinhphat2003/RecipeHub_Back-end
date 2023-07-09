package com.example.RecipeHub.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.RecipeHub.dtos.FIlterDTO;
import com.example.RecipeHub.dtos.ImageDTO;
import com.example.RecipeHub.dtos.IngredientDTO;
import com.example.RecipeHub.dtos.RecipeDTO;
import com.example.RecipeHub.dtos.RecipesPaginationResponse;
import com.example.RecipeHub.dtos.TagDTO;
import com.example.RecipeHub.entities.Image;
import com.example.RecipeHub.entities.Ingredient;
import com.example.RecipeHub.entities.MailInfo;
import com.example.RecipeHub.entities.Recipe;
import com.example.RecipeHub.entities.Tag;
import com.example.RecipeHub.entities.User;
import com.example.RecipeHub.enums.PrivacyStatus;
import com.example.RecipeHub.errorHandlers.ForbiddenExeption;
import com.example.RecipeHub.errorHandlers.NotFoundExeption;
import com.example.RecipeHub.mappers.RecipeMapper;
import com.example.RecipeHub.repositories.RecipeCustomRepository;
import com.example.RecipeHub.repositories.RecipeRepository;
import com.example.RecipeHub.utils.FileUtil;
import com.example.RecipeHub.utils.PaginationUtil;
import com.example.RecipeHub.utils.SystemUtil;
import com.example.RecipeHub.utils.TagDefaultConstant;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

@Service
public class RecipeService {

	private final RecipeRepository recipeRepository;
	private final TagService tagService;
	private final RecipeCustomRepository recipeCustomRepository;
	private final IngredientService ingredientService;
	private final ImageService imageService;
	private static final String SHARE_RECIPE_EMAIL_HTML_TEMPLATE = "share-recipe-email";
	private final EmailService emailService;
	private final UserService userService;

	public RecipeService(RecipeRepository recipeRepository, TagService tagService,
			RecipeCustomRepository recipeCustomRepository, IngredientService ingredientService,
			ImageService imageService, EmailService emailService, UserService userService) {
		super();
		this.recipeRepository = recipeRepository;
		this.tagService = tagService;
		this.recipeCustomRepository = recipeCustomRepository;
		this.ingredientService = ingredientService;
		this.imageService = imageService;
		this.emailService = emailService;
		this.userService = userService;
		;
	}

	public Pageable generatePageable(Integer page, Integer size, String sortBy, String direction) {
		if (sortBy == null || sortBy.isEmpty())
			return PageRequest.of(page, size);
		else if (direction.equals("desc"))
			return PageRequest.of(page, size, Sort.by(Direction.DESC, sortBy));
		else if (direction.equals("asc"))
			return PageRequest.of(page, size, Sort.by(Direction.ASC, sortBy));
		else
			return PageRequest.of(page, size, Sort.by(sortBy));
	}

	public RecipeDTO getRecipeDTOById(Long recipeId) {
		Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new NotFoundExeption("recipe not found"));
		return RecipeMapper.INSTANCE.recipeToRecipeDto(recipe);
	}

	public Recipe getRecipeById(long recipeId) {
		Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new NotFoundExeption("recipe not found"));
		return recipe;
	}

	public ArrayList<RecipeDTO> getAllRecipesByUser(User user) {
		List<Recipe> recipes = user.getRecipes();
		ArrayList<RecipeDTO> recipeDTOs = new ArrayList<>();
		for (Recipe recipe : recipes)
			recipeDTOs.add(RecipeMapper.INSTANCE.recipeToRecipeDto(recipe));
		return recipeDTOs;
	}

	public void save(Recipe recipe) {
		recipeRepository.save(recipe);
	}

	public ArrayList<RecipeDTO> getRecipesByPrivacyStatus(PrivacyStatus privacyStatus, Integer page, Integer size,
			String sortBy, String direction) {
		Page<Recipe> recipes = recipeRepository.findAllByPrivacyStatus(privacyStatus,
				generatePageable(page, size, sortBy, direction));
		ArrayList<RecipeDTO> recipeDTOs = new ArrayList<>();
		for (Recipe recipe : recipes)
			recipeDTOs.add(RecipeMapper.INSTANCE.recipeToRecipeDto(recipe));
		return recipeDTOs;
	}

	public RecipesPaginationResponse getRecipesWithPaginationAndFilter(String query, int page, int size, String sort,
			String direction) {
		List<Recipe> recipes = recipeCustomRepository.filterByCondition(null, null, page, size, sort, direction, query,
				null, null, null);
		Long totalItem = recipeCustomRepository.getCountOfFilterByCondition(null, null, page, size, sort, direction,
				query, null, null, null);
		ArrayList<RecipeDTO> recipeDTOs = new ArrayList<>();
		for (Recipe recipe : recipes)
			recipeDTOs.add(RecipeMapper.INSTANCE.recipeToRecipeDto(recipe));
		return new RecipesPaginationResponse(recipeDTOs, totalItem);
	}

	public ArrayList<RecipeDTO> getRecipesWithFilter(FIlterDTO fIlterDTO, int page, int size, Long userId) {
		switch (fIlterDTO.getSortBy()) {
		case "recent": {
			fIlterDTO.setSortBy("recipe_id");
			break;
		}
		case "title": {
			fIlterDTO.setSortBy("title");
			break;
		}
		case "rating": {
			fIlterDTO.setSortBy("rating");
			break;
		}
		case "yield": {
			fIlterDTO.setSortBy("recipe_yield");
			break;
		}
		case "time": {
			fIlterDTO.setSortBy("cook_time");
			break;
		}
		default:
			fIlterDTO.setSortBy("recipe_id");
		}

		switch (fIlterDTO.getDirection()) {
		case "desc": {
			fIlterDTO.setDirection("desc");
			break;
		}
		case "asc": {
			fIlterDTO.setDirection("asc");
			break;
		}
		default:
			fIlterDTO.setDirection("desc");
		}
		List<Recipe> recipes = recipeCustomRepository.filterByCondition(fIlterDTO.getTags(), fIlterDTO.getIngredients(),
				page, size, fIlterDTO.getSortBy(), fIlterDTO.getDirection(), fIlterDTO.getTitle(),
				fIlterDTO.getPrivacyStatus(), fIlterDTO.isFavorite(), userId);
		ArrayList<RecipeDTO> recipeDTOs = new ArrayList<>();
		for (Recipe recipe : recipes)
			recipeDTOs.add(RecipeMapper.INSTANCE.recipeToRecipeDto(recipe));
		return recipeDTOs;
	}

	public Long getTotalItemOfRecipesWithFilter(FIlterDTO fIlterDTO, int page, int size, Long userId) {
		switch (fIlterDTO.getSortBy()) {
		case "id": {
			fIlterDTO.setSortBy("recipe_id");
			break;
		}
		case "title": {
			fIlterDTO.setSortBy("title");
			break;
		}
		case "rating": {
			fIlterDTO.setSortBy("rating");
			break;
		}
		case "yield": {
			fIlterDTO.setSortBy("recipe_yield");
			break;
		}
		case "time": {
			fIlterDTO.setSortBy("cook_time");
			break;
		}
		default:
			fIlterDTO.setSortBy("recipe_id");
		}

		switch (fIlterDTO.getDirection()) {
		case "desc": {
			fIlterDTO.setDirection("desc");
			break;
		}
		case "asc": {
			fIlterDTO.setDirection("asc");
			break;
		}
		default:
			fIlterDTO.setDirection("desc");
		}
		Long totalItem = recipeCustomRepository.getCountOfFilterByCondition(fIlterDTO.getTags(),
				fIlterDTO.getIngredients(), page, size, fIlterDTO.getSortBy(), fIlterDTO.getDirection(),
				fIlterDTO.getTitle(), fIlterDTO.getPrivacyStatus(), fIlterDTO.isFavorite(), userId);
		return totalItem;
	}

	@Value("${recipe.image.upload.directory}")
	private String recipeImagePath;

	public void addNewRecipe(RecipeDTO dto, MultipartFile[] imageFiles, Long userId,
			HttpServletRequest httpServletRequest) {
		Recipe recipe = RecipeMapper.INSTANCE.recipeDtoToRecipe(dto);
		for (TagDTO tagDTO : dto.getTags()) {
			Tag tagPersisted = tagService.getTagByNameAndUserId(tagDTO.getTagName(), userId);
			if (tagPersisted != null) {
				recipe.getTags().add(tagPersisted);
			} else {
				recipe.getTags().add(new Tag(null, tagDTO.getTagName(), null));
			}
		}
		for (IngredientDTO ingredientDTO : dto.getIngredients()) {
			recipe.getIngredients()
					.add(new Ingredient(null, recipe, ingredientDTO.getIngredientName(), ingredientDTO.getAmount()));
		}

		if (imageFiles != null)
			recipe = FileUtil.saveRecipeImage(imageFiles, recipe, recipeImagePath, httpServletRequest);
		recipeRepository.save(recipe);

	}

	@Transactional
	public void deleteOneUserRecipeById(Long recipeId, Long userId) {
		Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new NotFoundExeption("recipe not found"));
		if (recipe.getUser().getUserId() != userId)
			throw new ForbiddenExeption("this recipe is not your");
		Iterator<Image> imageIterator = recipe.getImages().iterator();
		while (imageIterator.hasNext()) {
			Image image = imageIterator.next();
			recipe.getImages().remove(image);
			FileUtil.deleteImage(recipeImagePath,
					image.getImageUrl().substring(image.getImageUrl().lastIndexOf('/')).substring(1));
			imageService.deleteById(image.getImageId());
			imageIterator = recipe.getImages().iterator();
		}
		recipeRepository.deleteTagAndRecipeLinks(recipe.getRecipe_id());
		recipeRepository.deleteById(recipe.getRecipe_id());
	}

	public void deleteOneRecipeById(Long recipeId) {
		Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new NotFoundExeption("recipe not found"));
		Iterator<Image> imageIterator = recipe.getImages().iterator();
		while (imageIterator.hasNext()) {
			Image image = imageIterator.next();
			recipe.getImages().remove(image);
			FileUtil.deleteImage(recipeImagePath,
					image.getImageUrl().substring(image.getImageUrl().lastIndexOf('/')).substring(1));
			imageService.deleteById(image.getImageId());
			imageIterator = recipe.getImages().iterator();
		}
		recipeRepository.deleteTagAndRecipeLinks(recipe.getRecipe_id());
		recipeRepository.deleteById(recipe.getRecipe_id());
	}

	public void editRecipe(RecipeDTO dto, Long recipeId, Long userId, MultipartFile[] files,
			HttpServletRequest httpServletRequest) {
		Recipe recipe = recipeRepository.findById(recipeId)
				.orElseThrow(() -> new NotFoundExeption("thí recipe is not existed"));
		if (recipe.getUser().getUserId() != userId)
			throw new ForbiddenExeption("this is not your own recipe");

		recipe.setTitle(dto.getTitle());
		recipe.setPre_time(dto.getPre_time());
		recipe.setCook_time(dto.getCook_time());
		recipe.setRecipe_yield(dto.getRecipe_yield());
		recipe.setRating(dto.getRating());
		recipe.setIs_favourite(dto.isIs_favourite());
		recipe.setDescription(dto.getDescription());
		recipe.setUnit(dto.getUnit());
		recipe.setSteps(dto.getSteps());
		recipe.setPrivacyStatus(PrivacyStatus.valueOf(dto.getPrivacyStatus()));

		// tags
		ArrayList<TagDTO> tagDTOs = dto.getTags();

		Iterator<Tag> iterator = recipe.getTags().iterator();
		ArrayList<String> defautTags = TagDefaultConstant.TAGS_DEFAULT;
		// delete tags
		while (iterator.hasNext()) {
			Tag tag = iterator.next();
			boolean check = true;
			for (TagDTO tagDTO : tagDTOs) {
				if (tag.getTagName().equals(tagDTO.getTagName())) {
					check = false;
					break;
				}
			}
			if (check) {
				if (defautTags.contains(tag.getTagName())) {
					recipe.getTags().remove(tag);
					iterator = recipe.getTags().iterator();
				} else {
					recipe.getTags().remove(tag);
					iterator = recipe.getTags().iterator();
				}
			}
		}
		// add tags
		for (TagDTO tagDTO : tagDTOs) {
			boolean check = true;
			for (Tag tag : recipe.getTags()) {
				if (tagDTO.getTagName().equals(tag.getTagName())) {
					check = false;
					break;
				}
			}
			if (check) {
				if (defautTags.contains(tagDTO.getTagName())) {
					Tag newTag = tagService.getByTagName(tagDTO.getTagName());
					recipe.getTags().add(newTag);
				} else {
					Tag newTag = null;
					try {
						newTag = tagService.getByTagName(tagDTO.getTagName());
					} catch (Exception e) {
						newTag = tagService.save(new Tag(tagDTO.getTagName()));
					}
					recipe.getTags().add(newTag);
				}
			}
		}

		// ingredients
		ArrayList<IngredientDTO> ingredientDTOs = dto.getIngredients();
		Iterator<Ingredient> iterator2 = recipe.getIngredients().iterator();
		while (iterator2.hasNext()) {
			Ingredient ingredient = iterator2.next();
			recipe.getIngredients().remove(ingredient);
			ingredientService.deleteById(ingredient.getIngredientId());
			iterator2 = recipe.getIngredients().iterator();
		}
		recipe = recipeRepository.save(recipe);
		for (IngredientDTO ingredientDTO : ingredientDTOs) {
			recipe.getIngredients()
					.add(new Ingredient(null, recipe, ingredientDTO.getIngredientName(), ingredientDTO.getAmount()));
		}

		// image
		// delete image
		ArrayList<ImageDTO> imageDTOs = dto.getImages();
		Iterator<Image> imageIterator = recipe.getImages().iterator();
		while (imageIterator.hasNext()) {
			Image image = imageIterator.next();
			boolean check = true;
			for (ImageDTO imageDTO : imageDTOs) {
				if (image.getImageId().equals(imageDTO.getImageId())) {
					check = false;
					break;
				}
			}
			if (check) {
				recipe.getImages().remove(image);
				imageIterator = recipe.getImages().iterator();
				FileUtil.deleteImage(recipeImagePath,
						image.getImageUrl().substring(image.getImageUrl().lastIndexOf('/')).substring(1));
				imageService.deleteById(image.getImageId());
			}
		}
		// add image
		if (files != null) {
			recipe = FileUtil.saveRecipeImage(files, recipe, recipeImagePath, httpServletRequest);
		}

		recipeRepository.save(recipe);
	}

	public Long copyRecipe(User user, Long recipeId, HttpServletRequest httpServletRequest) {
		Recipe recipe = recipeRepository.findById(recipeId)
				.orElseThrow(() -> new NotFoundExeption("this recipe is not ecisted"));
		if (recipe.getPrivacyStatus() == PrivacyStatus.PRIVATE)
			throw new ForbiddenExeption("you have no permission to copy this recipe");
		Recipe copiedRecipe = new Recipe();
		copiedRecipe.setTitle(recipe.getTitle());
		copiedRecipe.setPre_time(recipe.getPre_time());
		copiedRecipe.setCook_time(recipe.getCook_time());
		copiedRecipe.setRecipe_yield(recipe.getRecipe_yield());
		copiedRecipe.setRating(recipe.getRating());
		copiedRecipe.setIs_favourite(recipe.isIs_favourite());
		copiedRecipe.setDescription(recipe.getDescription());
		copiedRecipe.setUnit(recipe.getUnit());
		copiedRecipe.setNutrition(recipe.getNutrition());
		copiedRecipe.setSteps(recipe.getSteps());
		copiedRecipe.setPrivacyStatus(PrivacyStatus.PUBLIC);

		copiedRecipe = recipeRepository.save(copiedRecipe);

		copiedRecipe.setUser(user);

		// add ingredients
		for (Ingredient ingredient : recipe.getIngredients()) {
			copiedRecipe.getIngredients()
					.add(new Ingredient(null, copiedRecipe, ingredient.getIngredientName(), ingredient.getAmount()));
		}

		// add tags
		ArrayList<String> defautTags = TagDefaultConstant.TAGS_DEFAULT;
		for (Tag tag : recipe.getTags()) {
			if (defautTags.contains(tag.getTagName())) {
				Tag newTag = tagService.getByTagName(tag.getTagName());
				copiedRecipe.getTags().add(newTag);
			} else {
				Tag newTag = tagService.getTagByNameAndUserId(tag.getTagName(), user.getUserId());
				if (newTag == null)
					newTag = tagService.save(new Tag(tag.getTagName()));
				copiedRecipe.getTags().add(newTag);
			}
		}

		// add images
		for (Image image : recipe.getImages()) {
			String imageUrl = SystemUtil.getRecipeImagePath(httpServletRequest) + FileUtil.copyImage(
					image.getImageUrl().substring(image.getImageUrl().lastIndexOf('/')).substring(1), recipeImagePath);
			copiedRecipe.getImages().add(new Image(null, imageUrl, copiedRecipe));
		}

		copiedRecipe = recipeRepository.save(copiedRecipe);
		return copiedRecipe.getRecipe_id();
	}

	public void sendSharedRecipeEmailUsingHtmlTemplate(String fullname, String email, Long recipeid) throws Exception {
		final String subject = "Recipe share from your friend";
		Map<String, Object> properties = new HashMap<>();
		properties.put("name", fullname);
		properties.put("recipeId", recipeid);
		String sharedRecipeUrl = "https://recipehub-g3.vercel.app/global/" + recipeid;
		properties.put("sharedRecipeUrl", sharedRecipeUrl);
		MailInfo mailInfo = new MailInfo(email, subject, SHARE_RECIPE_EMAIL_HTML_TEMPLATE,
				properties);
		emailService.sendEmailUsingHTMLTemplate(mailInfo);
	}

}
