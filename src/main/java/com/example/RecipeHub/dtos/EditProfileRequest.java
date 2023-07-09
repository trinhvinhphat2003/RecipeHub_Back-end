package com.example.RecipeHub.dtos;

import com.example.RecipeHub.enums.Gender;

public class EditProfileRequest {
	private String fullname;
	private Long birthday;
	private Gender gender;
	public String getFullname() {
		return fullname;
	}
	public void setFullname(String fullname) {
		this.fullname = fullname;
	}
	public Long getBirthday() {
		return birthday;
	}
	public void setBirthday(Long birthday) {
		this.birthday = birthday;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	public EditProfileRequest(String fullname, Long birthday, Gender gender) {
		super();
		this.fullname = fullname;
		this.birthday = birthday;
		this.gender = gender;
	}
	public EditProfileRequest() {
		super();
	}
	
}
