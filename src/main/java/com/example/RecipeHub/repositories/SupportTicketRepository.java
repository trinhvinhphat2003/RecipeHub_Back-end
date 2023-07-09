package com.example.RecipeHub.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.example.RecipeHub.entities.SupportTicket;

public interface SupportTicketRepository extends JpaRepository<SupportTicket, Long>{
	@Query(value = "SELECT * FROM recipehub_db.support_ticket where email like %:query% or message like %:query% order by :sortBy :direction", nativeQuery = true)
	public Page<SupportTicket> findAllWithFilterAndPagination(@Param("query") String query, @Param("sortBy") String sortBy,@Param("direction") String direction, Pageable pageable);
	
	@Query(value = "SELECT * FROM recipehub_db.support_ticket where email like %:query% or message like %:query%", nativeQuery = true)
	public List<SupportTicket> countFindAllWithFilter(@Param("query") String query);
}
