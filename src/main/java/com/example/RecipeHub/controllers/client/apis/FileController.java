package com.example.RecipeHub.controllers.client.apis;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.UUID;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.RecipeHub.client.dtos.IngredientDTO;
import com.example.RecipeHub.client.dtos.RecipeDTO;
import com.example.RecipeHub.entities.Recipe;
import com.example.RecipeHub.entities.User;
import com.example.RecipeHub.repositories.UserRepository;
import com.example.RecipeHub.services.FileService;
import com.example.RecipeHub.services.RecipeService;
import com.example.RecipeHub.services.UserService;
import com.example.RecipeHub.utils.FileUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/")
public class FileController {

	private final RecipeService recipeService;
	private final FileService fileService;
	private final UserService userService;

	public FileController(RecipeService recipeService, FileService fileService, UserService userService) {
		super();
		this.recipeService = recipeService;
		this.fileService = fileService;
		this.userService = userService;
	}

	@GetMapping("user/file/recipe/excel")
	public void exportAllRecipesExcel(@AuthenticationPrincipal User user, HttpServletResponse response)
			throws IOException {
		fileService.extractAllRecipeToexcel(user, response);
	}

	@PostMapping("user/file/recipe/excel")
	public void exportRecipesExcelByIds(@AuthenticationPrincipal User user, HttpServletResponse response,
			@RequestBody Long[] ids) throws IOException {
		fileService.extractRecipeToexcelByIds(user, response, ids);
	}

	@Value("${avatar.image.upload.directory}")
	private String avatarDirectory;

	@PutMapping("user/image/avatar")
	public ResponseEntity<String> uploadAvatar(@AuthenticationPrincipal User user,
			@RequestParam("file") MultipartFile file, HttpServletRequest httpServletRequest) {
		String newFileName;
		if(user.getProfileImage().substring(user.getProfileImage().lastIndexOf('/')).substring(1).equals("default.jpg")) {
			newFileName = FileUtil.uploadImage(avatarDirectory, file);
			User userPersisted = userService.getUserById(user.getUserId());
			userPersisted.setProfileImage(getApplicationPath(httpServletRequest) + "/api/v1/global/image/avatar/" + newFileName);
			userService.save(userPersisted);
		} else {
			FileUtil.deleteImage(avatarDirectory, user.getProfileImage().substring(user.getProfileImage().lastIndexOf('/')).substring(1));
			newFileName = FileUtil.uploadImage(avatarDirectory, file);
			User userPersisted = userService.getUserById(user.getUserId());
			userPersisted.setProfileImage(getApplicationPath(httpServletRequest) + "/api/v1/global/image/avatar/" + newFileName);
			userService.save(userPersisted);
		}
		
		return ResponseEntity.ok("Image uploaded successfully");
	}

	@GetMapping("global/image/avatar/{image}")
	public ResponseEntity<byte[]> getAvatar(@PathVariable("image") String image) {
		
		MediaType mediaType = MediaType.IMAGE_JPEG;
		if(image.endsWith(".png"))  {
            mediaType = MediaType.IMAGE_PNG;
        } else if (image.endsWith(".gif")) {
            mediaType = MediaType.IMAGE_GIF;
        }
		
		byte[] imageBytes = FileUtil.getAllByteOfImage(avatarDirectory, image);
		
		return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(mediaType)
                .body(imageBytes);
	}
	
	@Value("${recipe.image.upload.directory}")
	private String recipeImagePath;
	
	@GetMapping("global/image/recipe/{image}")
	public ResponseEntity<byte[]> getRecipeImage(@PathVariable("image") String image) {
		
		MediaType mediaType = MediaType.IMAGE_JPEG;
		if(image.endsWith(".png"))  {
            mediaType = MediaType.IMAGE_PNG;
        } else if (image.endsWith(".gif")) {
            mediaType = MediaType.IMAGE_GIF;
        }
		
		byte[] imageBytes = FileUtil.getAllByteOfImage(recipeImagePath, image);
		
		return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(mediaType)
                .body(imageBytes);
	}
	
	private String getApplicationPath(HttpServletRequest request){
		return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	}
}
