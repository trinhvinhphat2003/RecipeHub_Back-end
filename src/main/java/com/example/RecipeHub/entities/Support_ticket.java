package com.example.RecipeHub.entities;

import java.util.Date;
import java.util.List;

import com.example.RecipeHub.enums.Gender;
import com.example.RecipeHub.enums.Role;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "support_ticket")
//@Data
//@NoArgsConstructor
//@AllArgsConstructor
//@Builder
public class Support_ticket {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "support_ticket_id")
	private Long support_ticket_id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;

	@Column(name = "message",nullable = false, columnDefinition = "text")
	private String message;
	
	@Column(name = "status" ,nullable = false)
	private Long status;

	public Long getSupport_ticket_id() {
		return support_ticket_id;
	}

	public void setSupport_ticket_id(Long support_ticket_id) {
		this.support_ticket_id = support_ticket_id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Long getStatus() {
		return status;
	}

	public void setStatus(Long status) {
		this.status = status;
	}

	public Support_ticket(Long support_ticket_id, User user, String message, Long status) {
		super();
		this.support_ticket_id = support_ticket_id;
		this.user = user;
		this.message = message;
		this.status = status;
	}

	public Support_ticket() {
		super();
	}
	
	//
	
	
}
