package com.example.RecipeHub.services.impl;

import org.springframework.stereotype.Service;

import com.example.RecipeHub.repositories.ImageRepository;
import com.example.RecipeHub.services.ImageService;

@Service
public class ImageServiceImpl implements ImageService{
private final ImageRepository imageRepository;	
	
	public ImageServiceImpl(ImageRepository imageRepository) {
		super();
		this.imageRepository = imageRepository;
	}

	@Override
	public void deleteById(Long imageId) {
		imageRepository.deleteById(imageId);
	}
}
