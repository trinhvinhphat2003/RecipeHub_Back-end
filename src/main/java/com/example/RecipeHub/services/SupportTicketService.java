package com.example.RecipeHub.services;

import java.util.ArrayList;

import com.example.RecipeHub.dtos.SupportTicketDTO;

public interface SupportTicketService {
	public ArrayList<SupportTicketDTO> getAllDtosWithPagination(String query, Integer page, Integer size, String sortBy, String status, String direction);
	public Integer getCountOfDtos(String query, String status);
	public SupportTicketDTO getOneDto(Long supportTicketId);
	public void deleteOne(Long supportTicketId);
	public void addOne(SupportTicketDTO dto);
	public void acceptSupportTiket(Long supportTicketId);
	public void rejectSupportTiket(Long supportTicketId);
}
