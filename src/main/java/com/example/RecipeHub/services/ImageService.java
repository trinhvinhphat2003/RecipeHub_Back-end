package com.example.RecipeHub.services;

import org.springframework.stereotype.Service;

import com.example.RecipeHub.repositories.ImageRepository;

@Service
public class ImageService {
	
	private final ImageRepository imageRepository;	
	
	public ImageService(ImageRepository imageRepository) {
		super();
		this.imageRepository = imageRepository;
	}

	public void deleteById(Long imageId) {
		imageRepository.deleteById(imageId);
	}

}
