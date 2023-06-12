package com.example.RecipeHub.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.example.RecipeHub.dtos.ImageDTO;
import com.example.RecipeHub.entities.Image;

@Mapper
public interface ImageMapper {
	ImageMapper INSTANCE = Mappers.getMapper(ImageMapper.class);
	
	ImageDTO imageToImageDto(Image image);
}
