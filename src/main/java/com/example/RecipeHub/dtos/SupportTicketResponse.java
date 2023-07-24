package com.example.RecipeHub.dtos;

import java.util.ArrayList;

public class SupportTicketResponse {
	private ArrayList<SupportTicketDTO> supportTickets;
	private Integer totalItem;
	public SupportTicketResponse(ArrayList<SupportTicketDTO> supportTickets, Integer totalItem) {
		super();
		this.supportTickets = supportTickets;
		this.totalItem = totalItem;
	}
	public SupportTicketResponse() {
		super();
	}
	public ArrayList<SupportTicketDTO> getSupportTickets() {
		return supportTickets;
	}
	public void setSupportTickets(ArrayList<SupportTicketDTO> supportTickets) {
		this.supportTickets = supportTickets;
	}
	public Integer getTotalItem() {
		return totalItem;
	}
	public void setTotalItem(Integer totalItem) {
		this.totalItem = totalItem;
	}
	
}
