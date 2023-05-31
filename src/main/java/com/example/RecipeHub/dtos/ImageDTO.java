package com.example.RecipeHub.dtos;

public class ImageDTO {
	private Long imageId;
	private String imageUrl;
	public ImageDTO(Long imageId, String imageUrl) {
		super();
		this.imageId = imageId;
		this.imageUrl = imageUrl;
	}
	public ImageDTO() {
		super();
	}
	public Long getImageId() {
		return imageId;
	}
	public void setImageId(Long imageId) {
		this.imageId = imageId;
	}
	public String getImageUrl() {
		return imageUrl;
	}
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
}
