package com.example.RecipeHub.dtos;

import lombok.*;

import java.util.Date;
import java.util.List;
public class MealPlannerDto {
    private Long mealPlannerId;
    private Date date;
    private List<RecipeDTO> recipeDtos;
    private long userId;

    public MealPlannerDto() {
    }

    public MealPlannerDto(Long mealPlannerId, Date date, List<RecipeDTO> recipeDtos, long userId) {
        this.mealPlannerId = mealPlannerId;
        this.date = date;
        this.recipeDtos = recipeDtos;
        this.userId = userId;
    }

    public Long getMealPlannerId() {
        return mealPlannerId;
    }

    public void setMealPlannerId(Long mealPlannerId) {
        this.mealPlannerId = mealPlannerId;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<RecipeDTO> getRecipeDtos() {
        return recipeDtos;
    }

    public void setRecipeDtos(List<RecipeDTO> recipeDtos) {
        this.recipeDtos = recipeDtos;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
