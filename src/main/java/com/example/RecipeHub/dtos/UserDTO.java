package com.example.RecipeHub.dtos;

import java.util.Date;

import com.example.RecipeHub.entities.User;
import com.example.RecipeHub.enums.Gender;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
public class UserDTO {
	private Long userId;
	private String fullName;
	private String profileImage;
	private Date birthday;
	private String gender;
	private String role;

	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}

	public Long getUserId() {
		return userId;
	}
	public void setUserId(Long userId) {
		this.userId = userId;
	}
	public String getFullName() {
		return fullName;
	}
	public void setFullName(String fullName) {
		this.fullName = fullName;
	}
	public String getProfileImage() {
		return profileImage;
	}
	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}
	public UserDTO() {
		super();
	}
	public UserDTO(Long userId, String fullName, String profileImage, Date birthday, String gender, String role) {
		super();
		this.userId = userId;
		this.fullName = fullName;
		this.profileImage = profileImage;
		this.birthday = birthday;
		this.gender = gender;
		this.role = role;
	}
}
