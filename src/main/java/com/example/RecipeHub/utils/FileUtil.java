package com.example.RecipeHub.utils;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;


import com.example.RecipeHub.entities.Image;
import com.example.RecipeHub.entities.Recipe;
import com.example.RecipeHub.errorHandlers.BadRequestExeption;
import com.example.RecipeHub.errorHandlers.InternalExeption;

import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletRequest;

public class FileUtil {
	public static String uploadImage(String path, MultipartFile file) {
		try {
			if (file.isEmpty()) {
				throw new BadRequestExeption("No image file provided");
			}
			//create UUID
			UUID uuid = UUID.randomUUID();
			
			//get original name and extension of file
			String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
			String fileExtension = originalFileName.substring(originalFileName.lastIndexOf("."));
			
			//merge uuid with extension to create a new file name
			String newFileName = uuid.toString() + fileExtension;
			
			//get file path
//			File directory = new ClassPathResource(avatarDirectory).getFile();
			String filePath = path + newFileName;
			
			Files.copy(file.getInputStream(), Paths.get(filePath));

			//write file to above file path
//            FileOutputStream fos = new FileOutputStream(filePath);
//            fos.write(file.getBytes());
//            fos.close();
			return newFileName;
		} catch (Exception e) {
			e.printStackTrace();
			throw new InternalExeption("No image file provided");
		}
	}
	
	public static void deleteImage(String path, String fileName) {
		try {
			Files.delete(Paths.get(path + fileName));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static byte[] getAllByteOfImage(String path, String fileName) {
		String filepath = path + fileName;
		try {
			return Files.readAllBytes(Paths.get(filepath));
		} catch (Exception e) {
			e.printStackTrace();
			throw new BadRequestExeption("internal error");
		}
	}
	
	public static Recipe saveRecipeImage(MultipartFile[] imageFiles, Recipe recipe, String recipeImagePath, HttpServletRequest httpServletRequest) {
		if(imageFiles.length > 8) throw new BadRequestExeption("exceed the allowed amount");
		for(MultipartFile imageFile : imageFiles) {
			String imageName = uploadImage(recipeImagePath, imageFile);
			recipe.getImages().add(new Image(null, SystemUtil.getRecipeImagePath(httpServletRequest) + imageName, recipe));
		}	
		return recipe;
	}

	public static String copyImage(String imageName, String recipeImagePath) {
		File sourceFile = new File(recipeImagePath + imageName);
        if (!sourceFile.exists()) {
            throw new InternalExeption("Image does not exist.");
        }
        String fileExtension = getFileExtension(imageName);
        String newImageName = generateNewImageName(fileExtension);
        
        try {
            Path sourcePath = sourceFile.toPath();
            Path destinationFilePath = Path.of(recipeImagePath, newImageName);
            Files.copy(sourcePath, destinationFilePath, StandardCopyOption.REPLACE_EXISTING);
            System.out.println("Image copied successfully with new name: " + newImageName);
        } catch (IOException | java.io.IOException e) {
            e.printStackTrace();
        }
		return newImageName;
	}
	
	private static String getFileExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
            return fileName.substring(dotIndex + 1);
        }
        return "";
    }
	
	private static String generateNewImageName(String fileExtension) {
        String uniqueID = UUID.randomUUID().toString();
        return uniqueID + "." + fileExtension;
    }
}
