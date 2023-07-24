package com.example.RecipeHub.services;

import java.util.ArrayList;

import com.example.RecipeHub.dtos.TagDTO;
import com.example.RecipeHub.entities.Tag;

public interface TagService {

	public Tag getTagByName(String name);

	public Tag getTagByNameAndUserId(String tagName, Long userId);

	public ArrayList<TagDTO> getAllTagsByUserId(Long userId);

	public Tag save(Tag tag);

	public Tag getByTagName(String tagName);

	public void deleteTagById(Long tagId);

	public void deleteOneTagByIdForUser(Long tagId, Long userId);
}
