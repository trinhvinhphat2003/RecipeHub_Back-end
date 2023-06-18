package com.example.RecipeHub.utils;

import jakarta.servlet.http.HttpServletRequest;

public class SystemUtil {
	public static String getApplicationPath(HttpServletRequest request){
		return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	}
	
	public static String getAvatarImagePath(HttpServletRequest request){
		return "http://" + request.getServerName() + ":" + request.getServerPort() + "/api/v1/global/image/avatar/";
	}
	
	public static String getRecipeImagePath(HttpServletRequest request){
		return "http://" + request.getServerName() + ":" + request.getServerPort() + "/api/v1/global/image/recipe/";
	}
}
