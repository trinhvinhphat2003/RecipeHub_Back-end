package com.example.RecipeHub.dtos;

import lombok.Data;

@Data
public class VerificationTokenRequest {
    private UserDTO userDTO;
    private String token;
}
