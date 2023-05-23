package com.example.RecipeHub.dtos;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {
	private Long user_id;
	private String full_name;
	private String profile_image;
	private Date birthday;
	private String gender;
	private String role;
}
