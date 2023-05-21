package com.example.RecipeHub.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "support_ticket")
public class Support_ticket {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long support_ticket_id;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	private User user;
//	private Long user_id;
	@Column(nullable = false)
	private String message;
	@Column(nullable = false)
	private Long status;
}
