package com.example.RecipeHub.client.dtos;

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
	private Long friendshipRequestId;
	private UserDTO sender;
	private UserDTO receiver;
	private String status;

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

	public FriendshipRequestDTO(Long friendshipRequestId, UserDTO sender, UserDTO receiver, String status) {
		super();
		this.friendshipRequestId = friendshipRequestId;
		this.sender = sender;
		this.receiver = receiver;
		this.status = status;
	}
	public Long getFriendshipRequestId() {
		return friendshipRequestId;
	}
	public void setFriendshipRequestId(Long friendshipRequestId) {
		this.friendshipRequestId = friendshipRequestId;
	}
	public FriendshipRequestDTO() {
		super();
	}
	
	
}
