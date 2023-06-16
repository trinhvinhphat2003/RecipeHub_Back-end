package com.example.RecipeHub.dtos.request;

import jakarta.validation.constraints.NotBlank;

import java.sql.Date;
import java.util.List;

public class MealPlannerRequest {
    private Long mealPlannerId;
    @NotBlank
    private Date date;
    private List<Long> recipeIdList;
    private long userId;

    public MealPlannerRequest() {
    }

    public MealPlannerRequest(Long mealPlannerId, Date date, List<Long> recipeId, long userId) {
        this.mealPlannerId = mealPlannerId;
        this.date = date;
        this.recipeIdList = recipeId;
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

    public List<Long> getRecipeIdList() {
        return recipeIdList;
    }

    public void setRecipeIdList(List<Long> recipeIdList) {
        this.recipeIdList = recipeIdList;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
