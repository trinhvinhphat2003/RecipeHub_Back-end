package com.example.RecipeHub.services;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.RecipeHub.client.dtos.TagDTO;
import com.example.RecipeHub.entities.Tag;
import com.example.RecipeHub.errorHandlers.NotFoundExeption;
import com.example.RecipeHub.mappers.TagMapper;
import com.example.RecipeHub.repositories.TagRepository;

import jakarta.transaction.Transactional;

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
		return (tagRepository.findOneByUserIdAndName(tagName, userId)).orElse(null);	
	}
	
	public ArrayList<TagDTO> getAllTagsByUserId(Long userId) {
		ArrayList<String> defautTags = new ArrayList<>();
		defautTags.add("Breakfast");
		defautTags.add("Lunch");
		defautTags.add("Dinner");
		defautTags.add("Appetizer");
		defautTags.add("Dessert");
		defautTags.add("Drink");
		defautTags.add("Snack");
		List<Tag> tags = tagRepository.findByUserId(userId);
		ArrayList<TagDTO> dtos = new ArrayList<>();
		for(Tag tag : tags) {
			if(!defautTags.contains(tag.getTagName())) dtos.add(TagMapper.INSTANCE.tagToTagDto(tag));
		}
		return dtos;
	}

	public Tag save(Tag tag) {
		return tagRepository.save(tag);
	}

	public Tag getByTagName(String tagName) {
		return tagRepository.findByTagName(tagName).orElseThrow(() -> new NotFoundExeption("Tag is not existed"));
	}

	public void deleteTagById(Long tagId) {
		tagRepository.deleteById(tagId);
	}
}
