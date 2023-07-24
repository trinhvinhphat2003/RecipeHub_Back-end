package com.example.RecipeHub.controllers.client.apis;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.RecipeHub.dtos.SupportTicketDTO;
import com.example.RecipeHub.services.SupportTicketService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/")
public class SupportTicketController {
	
	private final SupportTicketService supportTicketService;
	
	public SupportTicketController(SupportTicketService supportTicketService) {
		super();
		this.supportTicketService = supportTicketService;
	}

	@PostMapping("global/support-ticket")
	public ResponseEntity<String> addNewSupportTicket(@Valid @RequestBody SupportTicketDTO dto) {
		supportTicketService.addOne(dto);
		return ResponseEntity.ok("add new support ticket successfully");
	}
	
	@GetMapping("admin/support-ticket/total")
	public ResponseEntity<Integer> countRecipeCurrentInDB() {
		return ResponseEntity.ok(supportTicketService.countSupportTicketCurrentInDB());
	}
}
