package com.example.RecipeHub.utils;

import jakarta.servlet.http.HttpServletRequest;

public class SystemUtil {
	public static String getApplicationPath(HttpServletRequest request){
		return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	}
}
