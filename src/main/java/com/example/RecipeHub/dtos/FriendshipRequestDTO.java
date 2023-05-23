package com.example.RecipeHub.dtos;

import com.example.RecipeHub.entities.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FriendshipRequestDTO {
	private Long friendship_request_id;
	private UserDTO sender;
	private UserDTO receiver;
	private String status;
}
