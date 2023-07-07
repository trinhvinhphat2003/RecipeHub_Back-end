package com.example.RecipeHub.eventListeners;

import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.example.RecipeHub.services.RecipeService;

@Component
public class RecipeEventListenner {
	
	private final RecipeService recipeService;
	
	public RecipeEventListenner(RecipeService recipeService) {
		super();
		this.recipeService = recipeService;
	}

	@Async
	@EventListener
	public void handleShareRecipeEvent(ShareRecipeEvent event) throws Exception {
		for(String email : event.getEmails()) {
			recipeService.sendSharedRecipeEmailUsingHtmlTemplate(event.getFullName(), email, event.getRecipeId());
		}
	}
}
