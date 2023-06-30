package com.example.RecipeHub.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.RecipeHub.client.dtos.TagDTO;
import com.example.RecipeHub.entities.Tag;

@Mapper(componentModel = "spring")
public interface TagMapper {
	TagMapper INSTANCE = Mappers.getMapper(TagMapper.class);
	
	TagDTO tagToTagDto(Tag tag);
}
