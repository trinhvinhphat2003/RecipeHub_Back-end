package com.example.RecipeHub.dtos;

import java.util.List;

import com.example.RecipeHub.entities.Recipe;
import com.example.RecipeHub.entities.Tag;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
public class TagDTO {
	private Long tagId;
	private String tagName;
	public Long getTagId() {
		return tagId;
	}
	public void setTagId(Long tagId) {
		this.tagId = tagId;
	}
	public String getTagName() {
		return tagName;
	}
	public void setTagName(String tagName) {
		this.tagName = tagName;
	}
	public TagDTO(Long tagId, String tagName) {
		super();
		this.tagId = tagId;
		this.tagName = tagName;
	}
	public TagDTO() {
		super();
	}
	
	
}
