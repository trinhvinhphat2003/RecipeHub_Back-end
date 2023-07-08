package com.example.RecipeHub.client.dtos;

import com.example.RecipeHub.enums.SupportTicketStatus;

public class SupportTicketDTO {
	private Long supportTicketId;
	private String email;
	private String message;
	private SupportTicketStatus status;
	public SupportTicketDTO(Long supportTicketId, String email, String message, SupportTicketStatus status) {
		super();
		this.supportTicketId = supportTicketId;
		this.email = email;
		this.message = message;
		this.status = status;
	}
	public SupportTicketDTO() {
		super();
	}
	public Long getSupportTicketId() {
		return supportTicketId;
	}
	public void setSupportTicketId(Long supportTicketId) {
		this.supportTicketId = supportTicketId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
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
	
	
}
