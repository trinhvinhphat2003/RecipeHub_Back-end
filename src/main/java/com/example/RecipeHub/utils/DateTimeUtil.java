package com.example.RecipeHub.utils;

import java.util.Date;

public class DateTimeUtil {
	public static Long dateToMilisecond(Date date) {
		return date.getTime();
	}
	
	public static Date milisecondToDate(Long milisecond) {
		return new Date(milisecond);
	}
}
