package com.example.RecipeHub.dtos;

public class ResponseObject {
	private String email;
	private String picture;
	private String given_name;
	private String family_name;
	private String name;
	public ResponseObject(String email, String picture, String given_name, String family_name, String name) {
		super();
		this.email = email;
		this.picture = picture;
		this.given_name = given_name;
		this.family_name = family_name;
		this.name = name;
	}
	public ResponseObject() {
		super();
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getGiven_name() {
		return given_name;
	}
	public void setGiven_name(String given_name) {
		this.given_name = given_name;
	}
	public String getFamily_name() {
		return family_name;
	}
	public void setFamily_name(String family_name) {
		this.family_name = family_name;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Override
	public String toString() {
		return "ResponseObject [email=" + email + ", picture=" + picture + ", given_name=" + given_name
				+ ", family_name=" + family_name + ", name=" + name + "]";
	}
	
}
