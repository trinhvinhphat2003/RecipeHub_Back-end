package com.example.RecipeHub.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.example.RecipeHub.dtos.SupportTicketDTO;
import com.example.RecipeHub.entities.SupportTicket;

@Mapper(componentModel = "spring")
public interface SupportTicketMapper {
	SupportTicketMapper INSTANCE = Mappers.getMapper(SupportTicketMapper.class);
	
	@Mappings({
		@Mapping(target = "message", source = "message"),
		@Mapping(target = "status", source = "status"),
		@Mapping(target = "email", source = "email"),
		@Mapping(target = "supportTicketId", source = "supportTicketId")
	})
	SupportTicketDTO entityToDto(SupportTicket supportTicket);
}
