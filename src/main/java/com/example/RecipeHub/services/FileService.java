package com.example.RecipeHub.services;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.UUID;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.example.RecipeHub.client.dtos.IngredientDTO;
import com.example.RecipeHub.client.dtos.RecipeDTO;
import com.example.RecipeHub.entities.Image;
import com.example.RecipeHub.entities.Recipe;
import com.example.RecipeHub.entities.User;
import com.example.RecipeHub.errorHandlers.BadRequestExeption;
import com.example.RecipeHub.errorHandlers.ForbiddenExeption;
import com.example.RecipeHub.errorHandlers.InternalExeption;

import jakarta.servlet.http.HttpServletResponse;

@Service
public class FileService {

	public final RecipeService recipeService;

	public FileService(RecipeService recipeService) {
		super();
		this.recipeService = recipeService;
	}

	public void extractAllRecipeToexcel(User user, HttpServletResponse response) throws IOException {
		ArrayList<RecipeDTO> recipes = recipeService.getAllRecipesByUser(user);

		// create a excel file holder
		Workbook workbook = new XSSFWorkbook();

		// create a sheet
		Sheet sheet = workbook.createSheet("Recipes");

		// set header
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

		// initial row
		int rowNum = 1;

		// get data from db and push into sheet
		for (RecipeDTO recipe : recipes) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(recipe.getRecipe_id());
			row.createCell(1).setCellValue(recipe.getTitle());
			row.createCell(2).setCellValue(recipe.getPre_time());
			row.createCell(3).setCellValue(recipe.getCook_time());
			StringBuilder ingredients = new StringBuilder();
			ArrayList<IngredientDTO> ingredientDTOs = recipe.getIngredients();
			for (IngredientDTO ingredientDTO : ingredientDTOs)
				ingredients.append(ingredientDTO.getIngredientName() + ": " + ingredientDTO.getAmount() + ", ");
			if(ingredientDTOs.size() == 0)ingredients.append(", ");
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

		// set response type for excel file
		response.setHeader("Content-Disposition", "attachment; filename=recipes.xlsx");
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

		// write from holder to actual file
		OutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);

		// close holder
		workbook.close();
		outputStream.close();
	}

	public void extractRecipeToexcelByIds(User user, HttpServletResponse response, Long[] recipeIds)
			throws IOException {
		ArrayList<RecipeDTO> recipeDTOs = new ArrayList<>();
		for (Long recipeId : recipeIds) {
			RecipeDTO recipeDto = recipeService.getRecipeDTOById(recipeId);
			if (recipeDto.getUserId() != user.getUserId())
				throw new ForbiddenExeption("you are not owner of this recipe");
			recipeDTOs.add(recipeDto);
		}

		// create a excel file holder
		Workbook workbook = new XSSFWorkbook();

		// create a sheet
		Sheet sheet = workbook.createSheet("Recipes");

		// set header
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

		// initial row
		int rowNum = 1;

		// get data from db and push into sheet
		for (RecipeDTO recipe : recipeDTOs) {
			Row row = sheet.createRow(rowNum++);
			row.createCell(0).setCellValue(recipe.getRecipe_id());
			row.createCell(1).setCellValue(recipe.getTitle());
			row.createCell(2).setCellValue(recipe.getPre_time());
			row.createCell(3).setCellValue(recipe.getCook_time());
			StringBuilder ingredients = new StringBuilder();
			ArrayList<IngredientDTO> ingredientDTOs = recipe.getIngredients();
			for (IngredientDTO ingredientDTO : ingredientDTOs)
				ingredients.append(ingredientDTO.getIngredientName() + ": " + ingredientDTO.getAmount() + ", ");
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
		// set response type for excel file
		response.setHeader("Content-Disposition", "attachment; filename=recipes.xlsx");
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");

		// write from holder to actual file
		OutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);

		// close holder
		workbook.close();
		outputStream.close();
	}
		
}
