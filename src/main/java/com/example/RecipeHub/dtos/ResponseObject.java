package com.example.RecipeHub.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResponseObject {
	private String email;
	private String picture;
	private String given_name;
	private String family_name;
	private String name;
}
