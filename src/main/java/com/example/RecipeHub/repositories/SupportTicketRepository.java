package com.example.RecipeHub.repositories;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.RecipeHub.entities.SupportTicket;

@Repository
public interface SupportTicketRepository extends JpaRepository<SupportTicket, Long>{
	@Query(value = "SELECT * FROM support_ticket where email like %:query% or message like %:query% and status like %:status% order by :sortBy :direction", nativeQuery = true)
	public Page<SupportTicket> findAllWithFilterAndPagination(@Param("query") String query, @Param("sortBy") String sortBy,@Param("direction") String direction, @Param("status") String status, Pageable pageable);
	
	@Query(value = "SELECT * FROM support_ticket where email like %:query% or message like %:query% and status like %:status%", nativeQuery = true)
	public List<SupportTicket> countFindAllWithFilter(@Param("query") String query, @Param("status") String status);

	@Query(value = "SELECT count(*) FROM support_ticket", nativeQuery = true)
	public Integer countSupportTicket();
}
