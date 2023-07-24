package com.example.RecipeHub.dtos;

import java.util.Date;

import com.example.RecipeHub.enums.Gender;
import com.example.RecipeHub.enums.LoginType;
import com.example.RecipeHub.enums.Role;

public class UserEntityJsonBinding {
    private String email;
    private String password;
    private String fullName;
    private String profileImage;
    private Date birthday;
    private Role role;
    private Gender gender;
    private LoginType loginType;
    private boolean enable;
    private boolean blocked;
	public UserEntityJsonBinding(String email, String password, String fullName, String profileImage, Date birthday,
			Role role, Gender gender, LoginType loginType, boolean enable, boolean blocked) {
		super();
		this.email = email;
		this.password = password;
		this.fullName = fullName;
		this.profileImage = profileImage;
		this.birthday = birthday;
		this.role = role;
		this.gender = gender;
		this.loginType = loginType;
		this.enable = enable;
		this.blocked = blocked;
	}
	public UserEntityJsonBinding() {
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
	public LoginType getLoginType() {
		return loginType;
	}
	public void setLoginType(LoginType loginType) {
		this.loginType = loginType;
	}
	public boolean isEnable() {
		return enable;
	}
	public void setEnable(boolean enable) {
		this.enable = enable;
	}
	public boolean isBlocked() {
		return blocked;
	}
	public void setBlocked(boolean blocked) {
		this.blocked = blocked;
	}
}