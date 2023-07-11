package com.example.RecipeHub.services.impl;

import com.example.RecipeHub.client.dtos.ForgottenPasswordDto;
import com.example.RecipeHub.entities.User;
import com.example.RecipeHub.repositories.UserRepository;
import com.example.RecipeHub.services.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void changePassword(String newPassword, User user){
//        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public ForgottenPasswordDto generatePassword(ForgottenPasswordDto request) throws Exception{
        // generate password & save to user account
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new RuntimeException("User not found"));
        String newPassword = UUID.randomUUID().toString();
        user.setPassword(passwordEncoder.encode(newPassword));
        return new ForgottenPasswordDto(request.getId(), request.getEmail(), request.getName(), newPassword);
    }
}
