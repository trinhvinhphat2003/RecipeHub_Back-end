package com.example.RecipeHub.eventListeners;

import org.springframework.context.ApplicationEvent;

public class ShareRecipeEvent extends ApplicationEvent{
	private String[] emails;
	private String fullName;
	private Long recipeId;
	
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public Long getRecipeId() {
		return recipeId;
	}

	public void setRecipeId(Long recipeId) {
		this.recipeId = recipeId;
	}

	public String[] getEmails() {
		return emails;
	}

	public void setEmails(String[] emails) {
		this.emails = emails;
	}

	public ShareRecipeEvent(String[] emails, String fullName, Long recipeId) {
		super(emails);
		this.emails = emails;
		this.fullName = fullName;
		this.recipeId = recipeId;
	}
}
