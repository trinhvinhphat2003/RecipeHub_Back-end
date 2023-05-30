package com.example.RecipeHub.dtos;

import java.util.Date;

import com.example.RecipeHub.entities.User;
import com.example.RecipeHub.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDTO {
	private Long userId;
	private String fullName;
	private String profileImage;
	private Date birthday;
	private String gender;
	private String role;
}
