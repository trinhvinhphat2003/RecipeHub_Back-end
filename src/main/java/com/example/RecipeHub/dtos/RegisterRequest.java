package com.example.RecipeHub.dtos;

import com.example.RecipeHub.enums.Gender;
import com.example.RecipeHub.enums.Role;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterRequest {
    @Email
    private String email;
    private String password;
    private String fullName;
    private String profileImage;
    private Date birthday;
    private Role role;
    private Gender gender;
}
