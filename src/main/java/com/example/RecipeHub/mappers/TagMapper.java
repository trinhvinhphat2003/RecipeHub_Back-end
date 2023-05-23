package com.example.RecipeHub.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.RecipeHub.dtos.TagDTO;
import com.example.RecipeHub.entities.Tag;

@Mapper
public interface TagMapper {
	TagMapper INSTANCE = Mappers.getMapper(TagMapper.class);
	
	TagDTO tagToTagDto(Tag tag);
}
