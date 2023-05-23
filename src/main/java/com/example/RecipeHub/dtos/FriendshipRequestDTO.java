package com.example.RecipeHub.dtos;

import com.example.RecipeHub.entities.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
public class FriendshipRequestDTO {
	private Long friendship_request_id;
	private UserDTO sender;
	private UserDTO receiver;
	private String status;
	public Long getFriendship_request_id() {
		return friendship_request_id;
	}
	public void setFriendship_request_id(Long friendship_request_id) {
		this.friendship_request_id = friendship_request_id;
	}
	public UserDTO getSender() {
		return sender;
	}
	public void setSender(UserDTO sender) {
		this.sender = sender;
	}
	public UserDTO getReceiver() {
		return receiver;
	}
	public void setReceiver(UserDTO receiver) {
		this.receiver = receiver;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public FriendshipRequestDTO(Long friendship_request_id, UserDTO sender, UserDTO receiver, String status) {
		super();
		this.friendship_request_id = friendship_request_id;
		this.sender = sender;
		this.receiver = receiver;
		this.status = status;
	}
	public FriendshipRequestDTO() {
		super();
	}
	
	
}
