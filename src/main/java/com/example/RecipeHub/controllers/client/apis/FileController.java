package com.example.RecipeHub.controllers.client.apis;

import java.io.IOException;
import java.io.OutputStream;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.RecipeHub.dtos.IngredientDTO;
import com.example.RecipeHub.dtos.RecipeDTO;
import com.example.RecipeHub.entities.Recipe;
import com.example.RecipeHub.entities.User;
import com.example.RecipeHub.services.FileService;
import com.example.RecipeHub.services.RecipeService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/")
public class FileController {

	private final RecipeService recipeService;
	private final FileService fileService;

	public FileController(RecipeService recipeService, FileService fileService) {
		super();
		this.recipeService = recipeService;
		this.fileService = fileService;
	}

	@Value("${image.upload.directory}")
	private String uploadDirectory;

	@GetMapping("user/file/recipe/excel")
	public void exportAllRecipesExcel(@AuthenticationPrincipal User user, HttpServletResponse response)
			throws IOException {
		fileService.extractAllRecipeToexcel(user, response);
	}

	@PostMapping("user/file/recipe/excel")
	public void exportRecipesExcelByIds(@AuthenticationPrincipal User user, HttpServletResponse response, @RequestBody Long[] ids)
			throws IOException {
		fileService.extractRecipeToexcelByIds(user, response, ids);
	}

	@PostMapping("user/image/avatar")
	public ResponseEntity<String> uploadAvatar(@AuthenticationPrincipal User user,
			@RequestParam("file") MultipartFile file) {
		try {
			if (file.isEmpty()) {
				return ResponseEntity.badRequest().body("No image file provided");
			}
			String fileName = UUID.randomUUID().toString();
			Path filePath = Paths.get(uploadDirectory, fileName);
			Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

			return ResponseEntity.ok("Image uploaded successfully");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body("internal error");
		}
	}
	
	@PutMapping("user/image/avatar")
	public ResponseEntity<String> changeAvatar(@AuthenticationPrincipal User user,
			@RequestParam("file") MultipartFile file) {
		try {
			if (file.isEmpty()) {
				return ResponseEntity.badRequest().body("No image file provided");
			}
			String fileName = UUID.randomUUID().toString();
			Path filePath = Paths.get(uploadDirectory, fileName);
			Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

			return ResponseEntity.ok("Image uploaded successfully");
		} catch (Exception e) {
			e.printStackTrace();
			return ResponseEntity.internalServerError().body("internal error");
		}
	}
}
