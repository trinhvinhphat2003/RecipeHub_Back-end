package com.example.RecipeHub.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.support.PageableUtils;
import org.springframework.stereotype.Service;

import com.example.RecipeHub.dtos.SupportTicketDTO;
import com.example.RecipeHub.entities.SupportTicket;
import com.example.RecipeHub.enums.SupportTicketStatus;
import com.example.RecipeHub.errorHandlers.NotFoundExeption;
import com.example.RecipeHub.mappers.SupportTicketMapper;
import com.example.RecipeHub.repositories.SupportTicketRepository;
import com.example.RecipeHub.services.SupportTicketService;
import com.example.RecipeHub.utils.PaginationUtil;

import jakarta.transaction.Transactional;

@Service
public class SupportTicketServiceImpl implements SupportTicketService{
	
	private final SupportTicketRepository supportTicketRepository;

	public SupportTicketServiceImpl(SupportTicketRepository supportTicketRepository) {
		super();
		this.supportTicketRepository = supportTicketRepository;
	}

	@Override
	public ArrayList<SupportTicketDTO> getAllDtosWithPagination(String query, Integer page, Integer size, String sortBy, String direction, String status) {
		Page<SupportTicket> supportTickets = supportTicketRepository.findAllWithFilterAndPagination(query, sortBy, direction, status, PaginationUtil.generatePageable(page, size, sortBy, direction));
		ArrayList<SupportTicketDTO> result = new ArrayList<>();
		for(SupportTicket supportTicket : supportTickets) {
			result.add(SupportTicketMapper.INSTANCE.entityToDto(supportTicket));
		}
		return result;
	}

	@Override
	public SupportTicketDTO getOneDto(Long supportTicketId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteOne(Long supportTicketId) {
		supportTicketRepository.deleteById(supportTicketId);
		
	}

	@Override
	public void addOne(SupportTicketDTO dto) {
		SupportTicket supportTicket = new SupportTicket();
		supportTicket.setEmail(dto.getEmail());
		supportTicket.setMessage(dto.getMessage());
		supportTicket.setStatus(SupportTicketStatus.PENDING);
		supportTicketRepository.save(supportTicket);
	}

	@Override
	public Integer getCountOfDtos(String query, String status) {
		List<SupportTicket> result = supportTicketRepository.countFindAllWithFilter(query, status);
		return result.size();
	}

	@Override
	public void acceptSupportTiket(Long supportTicketId) {
		SupportTicket supportTicket = supportTicketRepository.findById(supportTicketId).orElseThrow(() -> new NotFoundExeption("this support ticket is not existed"));
		supportTicket.setStatus(SupportTicketStatus.ACCEPTED);
		supportTicketRepository.save(supportTicket);
	}

	@Transactional
	@Override
	public void rejectSupportTiket(Long supportTicketId) {
		SupportTicket supportTicket = supportTicketRepository.findById(supportTicketId).orElseThrow(() -> new NotFoundExeption("this support ticket is not existed"));
		supportTicketRepository.delete(supportTicket);
	}

	@Override
	public Integer countSupportTicketCurrentInDB() {
		return supportTicketRepository.countSupportTicket();
	}

}
