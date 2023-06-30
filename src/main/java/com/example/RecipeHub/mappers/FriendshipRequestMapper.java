package com.example.RecipeHub.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.example.RecipeHub.client.dtos.FriendshipRequestDTO;
import com.example.RecipeHub.client.dtos.UserDTO;
import com.example.RecipeHub.entities.FriendshipRequest;
import com.example.RecipeHub.entities.User;
import com.example.RecipeHub.enums.Friendship_status;

@Mapper
public interface FriendshipRequestMapper {
	FriendshipRequestMapper INSTANCE = Mappers.getMapper(FriendshipRequestMapper.class);
	
	@Named("mapSender")
	static UserDTO mapSender(User sender) {
		return UserMapper.INSTANCE.userToUserDTO(sender);
	}
	@Named("mapReceiver")
	static UserDTO mapReceiver(User receiver) {
		return UserMapper.INSTANCE.userToUserDTO(receiver);
	}
	@Named("mapStatus")
	static String mapStatus(Friendship_status status) {
		return status.name();
	}
	
	
	@Mappings({
		@Mapping(target = "friendshipRequestId", source = "friendshipRequestId"),
		@Mapping(target = "sender", qualifiedByName = "mapSender", source = "sender"),
		@Mapping(target = "receiver", qualifiedByName = "mapReceiver", source = "receiver"),
		@Mapping(target = "status", qualifiedByName = "mapStatus")
	})
	FriendshipRequestDTO friendshipRequestToFriendshipRequestDTO(FriendshipRequest friendshipRequest);
}
