package com.example.RecipeHub.controllers.client.apis;

import java.util.ArrayList;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.RecipeHub.client.dtos.TagDTO;
import com.example.RecipeHub.services.TagService;

@RestController
@RequestMapping("/api/v1/")
public class TagController {
	
	private final TagService tagService;
	
	public TagController(TagService tagService) {
		super();
		this.tagService = tagService;
	}

	@GetMapping("global/tags/{user_id}")
	public ResponseEntity<ArrayList<TagDTO>> getAllUserTags(@PathVariable Long userId) {
		ArrayList<TagDTO> response = tagService.getAllTagsByUserId(userId);
		return ResponseEntity.ok(response);
	}

}
