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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.RecipeHub.dtos.IngredientDTO;
import com.example.RecipeHub.dtos.RecipeDTO;
import com.example.RecipeHub.entities.Recipe;
import com.example.RecipeHub.entities.User;
import com.example.RecipeHub.services.RecipeService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/v1/")
public class FileController {
	
	private final RecipeService recipeService;
	
	public FileController(RecipeService recipeService) {
		super();
		this.recipeService = recipeService;
	}
	
	@Value("${image.upload.directory}")
    private String uploadDirectory;

	@GetMapping("user/export-recipes/excel")
	public ResponseEntity<String> exportRecipesExcel(@AuthenticationPrincipal User user, HttpServletResponse response) throws IOException {
		ArrayList<RecipeDTO> recipes = recipeService.getAllRecipesByUser(user);
		
		Workbook workbook = new XSSFWorkbook();
		
		Sheet sheet = workbook.createSheet("Recipes");
		
		Row headerRow = sheet.createRow(0);
		headerRow.createCell(0).setCellValue("Recipe ID");
        headerRow.createCell(1).setCellValue("Title");
        headerRow.createCell(2).setCellValue("Preparation Time");
        headerRow.createCell(3).setCellValue("Cooking Time");
        headerRow.createCell(4).setCellValue("ingredients");
        headerRow.createCell(5).setCellValue("recipe yield");
        headerRow.createCell(6).setCellValue("rating");
        headerRow.createCell(7).setCellValue("description");
        headerRow.createCell(8).setCellValue("unit");
        headerRow.createCell(9).setCellValue("steps");
        headerRow.createCell(10).setCellValue("nutrition");
		
		int rowNum = 1;
		for (RecipeDTO recipe : recipes) {
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(recipe.getRecipe_id());
            row.createCell(1).setCellValue(recipe.getTitle());
            row.createCell(2).setCellValue(recipe.getPre_time());
            row.createCell(3).setCellValue(recipe.getCook_time());
            StringBuilder ingredients = new StringBuilder();
            ArrayList<IngredientDTO> ingredientDTOs = recipe.getIngredients();
            for(IngredientDTO ingredientDTO : ingredientDTOs) ingredients.append(ingredientDTO.getIngredientName() + ": " + ingredientDTO.getAmount() + ", ");
            String ingredientString = ingredients.toString();
            ingredientString = ingredientString.substring(0, ingredientString.length() - 2);
            row.createCell(4).setCellValue(ingredientString);
            row.createCell(5).setCellValue(recipe.getRecipe_yield());
            row.createCell(6).setCellValue(recipe.getRating());
            row.createCell(7).setCellValue(recipe.getDescription());
            row.createCell(8).setCellValue(recipe.getUnit());
            row.createCell(9).setCellValue(recipe.getSteps());
            row.createCell(10).setCellValue(recipe.getNutrition());
        }
		
		response.setHeader("Content-Disposition", "attachment; filename=recipes.xlsx");
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        
        OutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
		
		return new ResponseEntity<String>("", HttpStatus.OK);
	}
	
	@PostMapping("user/upload-image")
	public ResponseEntity<String> uploadRecipeImage(@AuthenticationPrincipal User user, @RequestParam("file") MultipartFile file) {
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
