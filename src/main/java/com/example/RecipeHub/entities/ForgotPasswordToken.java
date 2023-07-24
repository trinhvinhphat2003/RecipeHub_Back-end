package com.example.RecipeHub.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ForgotPasswordToken {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "forgotpassword_token_id")
	private Long forgotPassWordTokenId;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
	
	@Column(name = "token")
	private String token;

	public ForgotPasswordToken(Long forgotPassWordTokenId, User user, String token) {
		super();
		this.forgotPassWordTokenId = forgotPassWordTokenId;
		this.user = user;
		this.token = token;
	}

	public ForgotPasswordToken() {
		super();
	}

	public Long getForgotPassWordTokenId() {
		return forgotPassWordTokenId;
	}

	public void setForgotPassWordTokenId(Long forgotPassWordTokenId) {
		this.forgotPassWordTokenId = forgotPassWordTokenId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
}
