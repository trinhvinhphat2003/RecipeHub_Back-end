package com.example.RecipeHub.entities;

import com.example.RecipeHub.enums.Friendship_status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "friendship_request")
public class FriendshipRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long friendship_request_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id")
    private User sender;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receiver_id")
    private User receiver;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Friendship_status status;

	public FriendshipRequest(User sender, User receiver, Friendship_status status) {
		super();
		this.sender = sender;
		this.receiver = receiver;
		this.status = status;
	}

	public FriendshipRequest(Long friendship_request_id, User sender, User receiver, Friendship_status status) {
		super();
		this.friendship_request_id = friendship_request_id;
		this.sender = sender;
		this.receiver = receiver;
		this.status = status;
	}

	public FriendshipRequest() {
		super();
	}

	public Friendship_status getStatus() {
		return status;
	}

	public void setStatus(Friendship_status status) {
		this.status = status;
	}

	public Long getFriendship_request_id() {
		return this.friendship_request_id;
	}

	public void setFriendship_request_id(Long friendship_request_id) {
		this.friendship_request_id = friendship_request_id;
	}

	public User getSender() {
		return sender;
	}

	public void setSender(User sender) {
		this.sender = sender;
	}

	public User getReceiver() {
		return receiver;
	}

	public void setReceiver(User receiver) {
		this.receiver = receiver;
	}
    
    
}
