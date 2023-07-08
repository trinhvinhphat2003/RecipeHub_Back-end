package com.example.RecipeHub.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.RecipeHub.entities.SupportTicket;

public interface SupportTicketRepository extends JpaRepository<SupportTicket, Long>{
	public Page<SupportTicket> findAll(Pageable pageable);
}
