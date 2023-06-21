package com.example.RecipeHub.dtos;

import java.util.Date;

import com.example.RecipeHub.enums.LoginType;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
	private Long birthday;
	private String gender;
	private String role;
	private LoginType loginType;
	private String email;
	
	
	public UserDTO(Long userId, String fullName, String profileImage, Long birthday, String gender, String role,
			LoginType loginType, String email) {
		super();
		this.userId = userId;
		this.fullName = fullName;
		this.profileImage = profileImage;
		this.birthday = birthday;
		this.gender = gender;
		this.role = role;
		this.loginType = loginType;
		this.email = email;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public UserDTO() {
		super();
	}
	public LoginType getLoginType() {
		return loginType;
	}
	public void setLoginType(LoginType loginType) {
		this.loginType = loginType;
	}
	public Long getBirthday() {
		return birthday;
	}
	public void setBirthday(Long birthday) {
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
	
	
}
