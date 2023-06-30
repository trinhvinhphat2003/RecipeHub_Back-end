package com.example.RecipeHub.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateTimeUtil {
	public static Long dateToMilisecond(Date date) {
		return date.getTime();
	}
	
	public static Date milisecondToDate(Long milisecond) {
		return new Date(milisecond);
	}
	
	public static String milisecondToDateString(Long milisecond) {
		LocalDate date = LocalDate.ofEpochDay(milisecond / (24 * 60 * 60 * 1000));
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String formattedDate = date.format(formatter);
		return formattedDate;
	}
}
