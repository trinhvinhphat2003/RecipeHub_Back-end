package com.example.RecipeHub.client.dtos;

import org.springframework.web.multipart.MultipartFile;

public class FormAddRecipeWrapper {
	private MultipartFile[] files;
	private RecipeDTO dto;
}
