package com.example.RecipeHub.mappers;

import com.example.RecipeHub.dtos.RegisterRequest;
import lombok.RequiredArgsConstructor;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.RecipeHub.dtos.RegisterRequest;
import com.example.RecipeHub.dtos.UserDTO;
import com.example.RecipeHub.entities.User;
import com.example.RecipeHub.enums.Gender;
import com.example.RecipeHub.enums.Role;
import org.springframework.security.crypto.password.PasswordEncoder;

@Mapper(componentModel = "spring")
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

    @Named("mapPassword")
    static String mapPassword(String password, PasswordEncoder passwordEncoder){
        return passwordEncoder.encode(password);
    }

    @Mappings({
        @Mapping(target = "role", qualifiedByName = "mapRole", source = "role"),
        @Mapping(target = "gender", qualifiedByName = "mapGender")
    })
    UserDTO userToUserDTO(User user);

    @Mapping(source = "email", target = "email")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "profileImage", target = "profileImage")
    @Mapping(source = "birthday", target = "birthday")
    @Mapping(target = "role", constant = "USER")
    @Mapping(source = "gender", target = "gender")
    @Mapping(target = "enable", constant = "false")
    User registerRequestToUser(RegisterRequest registerRequest);
}
