package com.example.RecipeHub.services;

import java.util.ArrayList;

import com.example.RecipeHub.dtos.SupportTicketDTO;

public interface SupportTicketService {
	public ArrayList<SupportTicketDTO> getAllDtosWithPagination(Integer page, Integer size, String sortBy, String direction);
	public SupportTicketDTO getOneDto(Long supportTicketId);
	public void deleteOne(Long supportTicketId);
	public void addOne(SupportTicketDTO dto);
}
