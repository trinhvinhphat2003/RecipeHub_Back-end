package com.example.RecipeHub.services.impl;

import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.RecipeHub.client.dtos.ForgottenPasswordDto;
import com.example.RecipeHub.entities.User;
import com.example.RecipeHub.repositories.UserRepository;
import com.example.RecipeHub.services.AccountService;

@Service
public class AccountServiceImpl implements AccountService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AccountServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		super();
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

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
        userRepository.save(user);
        return new ForgottenPasswordDto(user.getUserId(), request.getEmail(), user.getFullName(), newPassword);
    }
}
