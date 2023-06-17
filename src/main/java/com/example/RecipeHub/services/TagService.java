package com.example.RecipeHub.services;

import org.springframework.stereotype.Service;

import com.example.RecipeHub.entities.Tag;
import com.example.RecipeHub.errorHandlers.NotFoundExeption;
import com.example.RecipeHub.repositories.TagRepository;

@Service
public class TagService {
	private final TagRepository tagRepository;

	public TagService(TagRepository tagRepository) {
		super();
		this.tagRepository = tagRepository;
	}
	
	public Tag getTagByName(String name) {
		return tagRepository.findByTagName(name).orElseThrow(() -> new NotFoundExeption(""));
	}

	public Tag getTagByNameAndUserId(String tagName, Long userId) {
		return (tagRepository.findByUserIdAndName(tagName, userId)).orElse(null);	
	}
}
