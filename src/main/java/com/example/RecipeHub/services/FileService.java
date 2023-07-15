package com.example.RecipeHub.services;

import java.io.IOException;

import com.example.RecipeHub.entities.User;

import jakarta.servlet.http.HttpServletResponse;

public interface FileService {
	public void extractAllRecipeToexcel(User user, HttpServletResponse response) throws IOException;

	public void extractRecipeToexcelByIds(User user, HttpServletResponse response, Long[] recipeIds) throws IOException;
}
