package com.example.RecipeHub.controllers.admin.apis;

import java.util.ArrayList;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.RecipeHub.dtos.SupportTicketDTO;
import com.example.RecipeHub.dtos.SupportTicketResponse;
import com.example.RecipeHub.entities.User;
import com.example.RecipeHub.services.SupportTicketService;

@RestController
@RequestMapping("/api/v1/admin")
public class SupportTicketAdminController {
	
	private final SupportTicketService supportTicketService;
	
	public SupportTicketAdminController(SupportTicketService supportTicketService) {
		super();
		this.supportTicketService = supportTicketService;
	}

	@GetMapping("/support-tickets")
	public ResponseEntity<SupportTicketResponse> getAllWithPagination(@AuthenticationPrincipal User user, @RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "size", defaultValue = "20") int size, @RequestParam(value = "sortBy", defaultValue = "support_ticket_id") String sortBy, @RequestParam(value = "direction", defaultValue = "desc") String direction, @RequestParam(value = "query", defaultValue = "") String query) {
		ArrayList<SupportTicketDTO> dtos = supportTicketService.getAllDtosWithPagination(query, page, size, sortBy, direction);
		Integer totalItem = supportTicketService.getCountOfDtos(query);
		SupportTicketResponse result = new SupportTicketResponse(dtos, totalItem);
		return ResponseEntity.ok(result);
	}
}
