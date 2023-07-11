package com.example.RecipeHub.mappers;

import com.example.RecipeHub.client.dtos.VerificationTokenRequest;
import com.example.RecipeHub.entities.VerificationToken;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

public interface VerificationTokenMapper {
    VerificationTokenMapper INSTANCE = Mappers.getMapper(VerificationTokenMapper.class);

    @Mapping(source = "token", target = "token")
    @Mapping(source = "userDTO", target = "user")
    VerificationToken verificationTokenRequestToVerificationToken(VerificationTokenRequest verificationTokenRequest);
}
