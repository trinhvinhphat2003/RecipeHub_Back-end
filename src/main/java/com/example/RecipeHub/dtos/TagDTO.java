package com.example.RecipeHub.dtos;

import java.util.List;

import com.example.RecipeHub.entities.Recipe;
import com.example.RecipeHub.entities.Tag;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class TagDTO {
	private Long tagId;
	private String tagName;
}
