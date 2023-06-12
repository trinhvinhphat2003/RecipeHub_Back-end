package com.example.RecipeHub.dtos;

import com.example.RecipeHub.enums.Gender;
import com.example.RecipeHub.enums.Role;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

public class RegisterRequest {
    @Email
    private String email;
    private String password;
    private String fullName;
    private String profileImage;
    private Date birthday;
    private Role role;
    private Gender gender;
	public RegisterRequest(@Email String email, String password, String fullName, String profileImage, Date birthday,
			Role role, Gender gender) {
		super();
		this.email = email;
		this.password = password;
		this.fullName = fullName;
		this.profileImage = profileImage;
		this.birthday = birthday;
		this.role = role;
		this.gender = gender;
	}
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
	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public Role getRole() {
		return role;
	}
	public void setRole(Role role) {
		this.role = role;
	}
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
    
    
}
