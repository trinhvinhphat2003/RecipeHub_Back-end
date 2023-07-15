package com.example.RecipeHub.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.RecipeHub.dtos.TagDTO;
import com.example.RecipeHub.entities.Tag;
import com.example.RecipeHub.errorHandlers.NotFoundExeption;
import com.example.RecipeHub.mappers.TagMapper;
import com.example.RecipeHub.repositories.TagRepository;
import com.example.RecipeHub.services.TagService;
import com.example.RecipeHub.utils.TagDefaultConstant;

import jakarta.transaction.Transactional;

@Service
public class TagServiceImpl implements TagService{
	private final TagRepository tagRepository;

	public TagServiceImpl(TagRepository tagRepository) {
		super();
		this.tagRepository = tagRepository;
	}
	
	@Override
	public Tag getTagByName(String name) {
		return tagRepository.findByTagName(name).orElseThrow(() -> new NotFoundExeption(""));
	}

	@Override
	public Tag getTagByNameAndUserId(String tagName, Long userId) {
		ArrayList<String> defaultTags = TagDefaultConstant.TAGS_DEFAULT;
		if(defaultTags.contains(tagName.toLowerCase())) return tagRepository.findByTagName(tagName.toLowerCase()).orElse(null);
		return (tagRepository.findOneByUserIdAndName(tagName, userId)).orElse(null);	
	}
	
	@Override
	public ArrayList<TagDTO> getAllTagsByUserId(Long userId) {
		ArrayList<String> defautTags = TagDefaultConstant.TAGS_DEFAULT;
		List<Tag> tags = tagRepository.findByUserId(userId);
		ArrayList<TagDTO> dtos = new ArrayList<>();
		for(Tag tag : tags) {
			if(!defautTags.contains(tag.getTagName())) dtos.add(TagMapper.INSTANCE.tagToTagDto(tag));
		}
		return dtos;
	}

	@Override
	public Tag save(Tag tag) {
		return tagRepository.save(tag);
	}

	@Override
	public Tag getByTagName(String tagName) {
		return tagRepository.findByTagName(tagName).orElseThrow(() -> new NotFoundExeption("Tag is not existed"));
	}

	@Override
	public void deleteTagById(Long tagId) {
		tagRepository.deleteById(tagId);
	}

	@Override
	@Transactional
	public void deleteOneTagByIdForUser(Long tagId, Long userId) {
		Tag tag = tagRepository.findById(tagId).orElseThrow(() -> new NotFoundExeption("tag not found"));
		tagRepository.deleteTagAndRecipeLinks(tag.getTagId());
		tagRepository.deleteById(tag.getTagId());
	}
}
