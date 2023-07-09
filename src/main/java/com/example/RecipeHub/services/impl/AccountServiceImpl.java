package com.example.RecipeHub.services.impl;

import java.util.HashMap;
import java.util.Optional;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.RecipeHub.dtos.ForgotPasswordVerifiedMailDTO;
import com.example.RecipeHub.dtos.ForgottenPasswordDto;
import com.example.RecipeHub.entities.ForgotPasswordToken;
import com.example.RecipeHub.entities.User;
import com.example.RecipeHub.errorHandlers.BadRequestExeption;
import com.example.RecipeHub.errorHandlers.NotFoundExeption;
import com.example.RecipeHub.eventListeners.events.ForgotPasswordEvent;
import com.example.RecipeHub.eventListeners.events.ForgotPasswordVerifiedMailEvent;
import com.example.RecipeHub.repositories.ForgotPasswordTokenRepository;
import com.example.RecipeHub.repositories.UserRepository;
import com.example.RecipeHub.services.AccountService;
import com.example.RecipeHub.services.JwtService;

import jakarta.transaction.Transactional;

@Service
public class AccountServiceImpl implements AccountService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ForgotPasswordTokenRepository forgotPasswordTokenRepository;
    private final JwtService jwtService;

    public AccountServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, ForgotPasswordTokenRepository forgotPasswordTokenRepository, JwtService jwtService) {
		super();
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.forgotPasswordTokenRepository = forgotPasswordTokenRepository;
		this.jwtService = jwtService;
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
        return new ForgottenPasswordDto(request.getEmail(), user.getFullName(), newPassword);
    }

    @Transactional
	@Override
	public ForgotPasswordEvent verifyForgotPasswordToken(String token) throws Exception {
		ForgotPasswordToken forgotPasswordToken = forgotPasswordTokenRepository.findByToken(token).orElseThrow(() -> new NotFoundExeption("this token is not matched with any user"));
		if(jwtService.isTokenExpired(token)) {
			throw new BadRequestExeption("this token is expired");
		}
		ForgottenPasswordDto dto = new ForgottenPasswordDto(forgotPasswordToken.getUser().getEmail(), null, null);
		forgotPasswordTokenRepository.delete(forgotPasswordToken);
		return new ForgotPasswordEvent(generatePassword(dto));
	}

    @Transactional
	@Override
	public ForgotPasswordVerifiedMailEvent generateVerifyTokenEmailEvent(ForgottenPasswordDto forgottenPasswordDto) {
		String token = jwtService.generateForgotPasswordToken(new HashMap<>());
		User user = userRepository.findByEmail(forgottenPasswordDto.getEmail()).orElseThrow(() -> new NotFoundExeption("this email does not match any user"));
		Optional<ForgotPasswordToken> forgotPasswordToken = forgotPasswordTokenRepository.findByUser(user);
		if(forgotPasswordToken.isPresent()) {
			forgotPasswordToken.get().setToken(token);
			forgotPasswordTokenRepository.save(forgotPasswordToken.get());
		} else {
			forgotPasswordTokenRepository.save(new ForgotPasswordToken(null, user, token));
		}		
		return new ForgotPasswordVerifiedMailEvent(new ForgotPasswordVerifiedMailDTO(user.getFullName(), user.getEmail(), token));
	}
}
