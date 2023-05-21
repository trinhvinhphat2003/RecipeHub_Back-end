package com.example.RecipeHub.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.example.RecipeHub.dtos.FriendshipRequestDTO;
import com.example.RecipeHub.entities.FriendshipRequest;
import com.example.RecipeHub.entities.User;
import com.example.RecipeHub.enums.Friendship_status;

@Mapper
public interface FriendshipRequestMapper {
	FriendshipRequestMapper INSTANCE = Mappers.getMapper(FriendshipRequestMapper.class);
	
	@Named("mapSender")
	static Long mapSender(User sender) {
		return sender.getUser_id();
	}
	@Named("mapReceiver")
	static Long mapReceiver(User receiver) {
		return receiver.getUser_id();
	}
	@Named("mapStatus")
	static String mapStatus(Friendship_status status) {
		return status.name();
	}
	
	
	@Mappings({
		@Mapping(target = "friendship_request_id", source = "friendship_request_id"),
		@Mapping(target = "sender_id", qualifiedByName = "mapSender", source = "sender"),
		@Mapping(target = "receiver_id", qualifiedByName = "mapReceiver", source = "receiver"),
		@Mapping(target = "status", qualifiedByName = "mapStatus")
	})
	FriendshipRequestDTO friendshipRequestToFriendshipRequestDTO(FriendshipRequest friendshipRequest);
}
