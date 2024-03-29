package com.example.RecipeHub.dtos;

import com.example.RecipeHub.enums.Gender;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class RegisterRequest {
	@NotBlank(message = "Email is required")
	@Email(message = "Email is not valid")
	private String email;
	@NotBlank(message = "Password is required")
	@Size(min = 6, message = "Password must have at least 6 charactor")
	private String password;
	@NotBlank(message = "Fullname is required")
	private String fullName;
	private String profileImage;
	@NotNull(message = "Birthday is required")
	private Long birthday;

	public Long getBirthday() {
		return birthday;
	}

	public void setBirthday(Long birthday) {
		this.birthday = birthday;
	}

	public RegisterRequest(@Email String email, String password, String fullName, String profileImage, Long birthday,
			Gender gender) {
		super();
		this.email = email;
		this.password = password;
		this.fullName = fullName;
		this.profileImage = profileImage;
		this.birthday = birthday;
		this.gender = gender;
	}

	private Gender gender;

	public RegisterRequest() {
		super();
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

}
