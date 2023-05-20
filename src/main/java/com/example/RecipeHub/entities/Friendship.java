package com.example.RecipeHub.entities;

import com.example.RecipeHub.enums.Friendship_status;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Friendship {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long friendship_id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user1_id")
    private User user_1;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user2_id")
    private User User_2;
    
    @Enumerated(EnumType.STRING)
    private Friendship_status status;

	public Friendship(User user_1, User user_2, Friendship_status status) {
		super();
		this.user_1 = user_1;
		User_2 = user_2;
		this.status = status;
	}

	public Friendship() {
		super();
	}

	public Friendship_status getStatus() {
		return status;
	}

	public void setStatus(Friendship_status status) {
		this.status = status;
	}
    
    
}
