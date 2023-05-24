package com.example.RecipeHub.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import com.example.RecipeHub.dtos.UserDTO;
import com.example.RecipeHub.entities.User;
import com.example.RecipeHub.enums.Gender;
import com.example.RecipeHub.enums.Role;

@Mapper
public interface UserMapper {
	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);
	@Named("mapRole")
    static String mapRole(Role role) {
        return role.name();
    }

    @Named("mapGender")
    static String mapGender(Gender gender) {
        return gender.name();
    }

    @Mappings({
        @Mapping(target = "role", qualifiedByName = "mapRole", source = "role"),
        @Mapping(target = "gender", qualifiedByName = "mapGender")
    })
    UserDTO userToUserDTO(User user);
}
