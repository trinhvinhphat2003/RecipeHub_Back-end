package com.example.RecipeHub.entities;

import java.util.Date;
import java.util.List;

import com.example.RecipeHub.enums.Gender;
import com.example.RecipeHub.enums.Role;
import com.example.RecipeHub.enums.SupportTicketStatus;

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
public class SupportTicket {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "supportTicketId")
	private Long supportTicketId;

	@Column(name = "email")
	private String email;
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "message",nullable = false, columnDefinition = "text")
	private String message;
	
	@Column(name = "status" ,nullable = false)
	@Enumerated(EnumType.STRING)
	private SupportTicketStatus status;

	public SupportTicket(Long supportTicketId, String email, String message, SupportTicketStatus status) {
		super();
		this.supportTicketId = supportTicketId;
		this.email = email;
		this.message = message;
		this.status = status;
	}

	public Long getSupportTicketId() {
		return supportTicketId;
	}

	public void setSupportTicketId(Long supportTicketId) {
		this.supportTicketId = supportTicketId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public SupportTicketStatus getStatus() {
		return status;
	}

	public void setStatus(SupportTicketStatus status) {
		this.status = status;
	}

	public SupportTicket() {
		super();
	}
	
	//
	
	
}
