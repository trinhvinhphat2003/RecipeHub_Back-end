package com.example.RecipeHub.dtos;

import java.util.ArrayList;

public class UsersPaginationResponse {
	private ArrayList<UserDTO> users;
	private int totalItem;
	public UsersPaginationResponse(ArrayList<UserDTO> users, int totalItem) {
		super();
		this.users = users;
		this.totalItem = totalItem;
	}
	public ArrayList<UserDTO> getUsers() {
		return users;
	}
	public void setUsers(ArrayList<UserDTO> users) {
		this.users = users;
	}
	public int getTotalItem() {
		return totalItem;
	}
	public void setTotalItem(int totalItem) {
		this.totalItem = totalItem;
	}
	
}
