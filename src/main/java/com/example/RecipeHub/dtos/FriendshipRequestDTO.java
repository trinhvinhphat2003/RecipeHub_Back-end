package com.example.RecipeHub.dtos;

public class FriendshipRequestDTO {
	private Long friendship_request_id;
	private Long sender_id;
	private Long receiver_id;
	private String status;

	public FriendshipRequestDTO(Long friendship_request_id, Long sender_id, Long receiver_id, String status) {
		super();
		this.friendship_request_id = friendship_request_id;
		this.sender_id = sender_id;
		this.receiver_id = receiver_id;
		this.status = status;
	}

	public FriendshipRequestDTO() {
		super();
	}

	public Long getFriendship_request_id() {
		return friendship_request_id;
	}

	public void setFriendship_request_id(Long friendship_request_id) {
		this.friendship_request_id = friendship_request_id;
	}

	public Long getSender_id() {
		return sender_id;
	}

	public void setSender_id(Long sender_id) {
		this.sender_id = sender_id;
	}

	public Long getReceiver_id() {
		return receiver_id;
	}

	public void setReceiver_id(Long receiver_id) {
		this.receiver_id = receiver_id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
