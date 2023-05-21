package com.example.RecipeHub.dtos;

import java.util.Date;

import com.example.RecipeHub.entities.User;
import com.example.RecipeHub.enums.Gender;

public class UserDTO {
	private Long user_id;
	private String full_name;
	private String profile_image;
	private Date birthday;
	private String gender;
	private String role;
	public UserDTO(Long user_id, String full_name, String profile_image, Date birthday, String gender, String role) {
		super();
		this.user_id = user_id;
		this.full_name = full_name;
		this.profile_image = profile_image;
		this.birthday = birthday;
		this.gender = gender;
	}
	public UserDTO() {
		super();
	}
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	public String getFull_name() {
		return full_name;
	}
	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}
	public String getProfile_image() {
		return profile_image;
	}
	public void setProfile_image(String profile_image) {
		this.profile_image = profile_image;
	}
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
	
	
}
