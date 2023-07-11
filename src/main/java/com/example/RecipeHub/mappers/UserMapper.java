package com.example.RecipeHub.mappers;

//<<<<<<< HEAD
//import com.example.RecipeHub.dtos.request.RegisterRequest;
//import org.mapstruct.*;
//import org.mapstruct.factory.Mappers;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//import com.example.RecipeHub.dtos.UserDTO;
//=======
import java.util.Date;

import com.example.RecipeHub.client.dtos.request.RegisterRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.RecipeHub.client.dtos.UserDTO;
//>>>>>>> 2f578391f0a7bdb7c5c45d3e6c9720112a4db132
import com.example.RecipeHub.entities.User;
import com.example.RecipeHub.enums.Gender;
import com.example.RecipeHub.enums.Role;
import com.example.RecipeHub.utils.DateTimeUtil;

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
//<<<<<<< HEAD
//
//    @Named("mapPassword")
//    static String mapPassword(String password, PasswordEncoder passwordEncoder){
//        return passwordEncoder.encode(password);
//    }
//
//=======
    
    @Named("mapBirthdayFromUser")
    static Long mapBirthdayFromUser(Date date) {
    	return DateTimeUtil.dateToMilisecond(date);
    }
    
//>>>>>>> 2f578391f0a7bdb7c5c45d3e6c9720112a4db132
    @Mappings({
        @Mapping(target = "role", qualifiedByName = "mapRole", source = "role"),
        @Mapping(target = "gender", qualifiedByName = "mapGender"),
        @Mapping(source = "birthday", qualifiedByName = "mapBirthdayFromUser", target = "birthday")
    })
    UserDTO userToUserDTO(User user);
    
    @Named("mapPassword")
    static String mapPassword(String password, PasswordEncoder passwordEncoder){
        return passwordEncoder.encode(password);
    }
   
    @Named("mapBirthday")
    static Date mapBirthday(Long milisecond) {
    	return DateTimeUtil.milisecondToDate(milisecond);
    }
    
//>>>>>>> 2f578391f0a7bdb7c5c45d3e6c9720112a4db132
    @Mapping(source = "email", target = "email")
    @Mapping(source = "password", target = "password")
    @Mapping(source = "fullName", target = "fullName")
    @Mapping(source = "profileImage", target = "profileImage")
    @Mapping(source = "birthday", qualifiedByName = "mapBirthday", target = "birthday")
    @Mapping(target = "role", constant = "USER")
    @Mapping(source = "gender", target = "gender")
    @Mapping(target = "enable", constant = "false")
    User registerRequestToUser(RegisterRequest registerRequest);
}
